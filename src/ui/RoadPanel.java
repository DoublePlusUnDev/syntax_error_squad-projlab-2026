package ui;
import gamelogic.GameLogic;
import gamelogic.Lane;
import gamelogic.Node;
import gamelogic.RoadNetwork;
import gamelogic.RoadSegment;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.swing.JPanel;

public class RoadPanel extends JPanel {
    class DisplayNode {
        float x, y;
        Node node;
        public DisplayNode(float x, float y, Node node) {
            this.x = x;
            this.y = y;
            this.node = node;
        }
    }

    class DisplayEdge {
        DisplayNode from, to;
        RoadSegment segment;
        public DisplayEdge(DisplayNode from, DisplayNode to, RoadSegment segment) {
            this.from = from;
            this.to = to;
            this.segment = segment;
        }
    }

    private List<DisplayNode> nodes = new ArrayList<>();
    private List<DisplayEdge> edges = new ArrayList<>();

    private GameLogic gameLogic;

    public RoadPanel(GameLogic gameLogic) {
        setBackground(UIStyles.backgroundColor);
        this.gameLogic = gameLogic;
        gameLogic.addGameStateChangeListener(this::update);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (gameLogic.getRoads().isEmpty()) 
                    return;
                
                setRoads(gameLogic.getRoads().get(0));
                repaint();
            }
        });
    }

    public void setRoads(RoadNetwork roads) {
        nodes.clear();
        edges.clear();

        if (roads == null) {
            return;
        }

        Map<Node, DisplayNode> nodeMap = new HashMap<>();
        Random random = new Random();
        int panelWidth = Math.max(getWidth(), 1);
        int panelHeight = Math.max(getHeight(), 1);

        for (Node node : roads.getNodes()) {
            DisplayNode displayNode = new DisplayNode(random.nextInt(panelWidth), random.nextInt(panelHeight), node);
            nodes.add(displayNode);
            nodeMap.put(node, displayNode);
        }

        for (RoadSegment segment : roads.getRoadSegments()) {
            DisplayNode from = nodeMap.get(segment.getStartPoint());
            DisplayNode to = nodeMap.get(segment.getEndPoint());
            if (from != null && to != null) {
                edges.add(new DisplayEdge(from, to, segment));
            }
        }

        applyFruchtermanReingold(panelWidth, panelHeight, random);
    }

    private void applyFruchtermanReingold(int panelWidth, int panelHeight, Random random) {
        int nodeCount = nodes.size();
        if (nodeCount < 2) {
            return;
        }

        float padding = 20f;
        float minX = padding;
        float minY = padding;
        float maxX = Math.max(minX + 1f, panelWidth - padding);
        float maxY = Math.max(minY + 1f, panelHeight - padding);

        for (DisplayNode node : nodes) {
            node.x = minX + random.nextFloat() * (maxX - minX);
            node.y = minY + random.nextFloat() * (maxY - minY);
        }

        float area = Math.max(1f, (maxX - minX) * (maxY - minY));
        float k = (float) Math.sqrt(area / nodeCount);
        float temperature = Math.max(1f, Math.min(panelWidth, panelHeight) * 0.25f);
        int iterations = 150;

        float[] dispX = new float[nodeCount];
        float[] dispY = new float[nodeCount];
        Map<DisplayNode, Integer> nodeIndex = new HashMap<>();
        for (int i = 0; i < nodeCount; i++) {
            nodeIndex.put(nodes.get(i), i);
        }

        for (int iter = 0; iter < iterations; iter++) {
            Arrays.fill(dispX, 0f);
            Arrays.fill(dispY, 0f);

            // Repulsive forces between all pairs of nodes.
            for (int i = 0; i < nodeCount; i++) {
                DisplayNode v = nodes.get(i);
                for (int j = i + 1; j < nodeCount; j++) {
                    DisplayNode u = nodes.get(j);

                    float dx = v.x - u.x;
                    float dy = v.y - u.y;
                    float dist = (float) Math.sqrt(dx * dx + dy * dy);
                    dist = Math.max(0.01f, dist);

                    float force = (k * k) / dist;
                    float fx = (dx / dist) * force;
                    float fy = (dy / dist) * force;

                    dispX[i] += fx;
                    dispY[i] += fy;
                    dispX[j] -= fx;
                    dispY[j] -= fy;
                }
            }

            // Attractive forces along edges.
            for (DisplayEdge edge : edges) {
                Integer fromIndex = nodeIndex.get(edge.from);
                Integer toIndex = nodeIndex.get(edge.to);
                if (fromIndex == null || toIndex == null || fromIndex.equals(toIndex)) {
                    continue;
                }

                DisplayNode v = nodes.get(fromIndex);
                DisplayNode u = nodes.get(toIndex);
                float dx = v.x - u.x;
                float dy = v.y - u.y;
                float dist = (float) Math.sqrt(dx * dx + dy * dy);
                dist = Math.max(0.01f, dist);

                float force = (dist * dist) / k;
                float fx = (dx / dist) * force;
                float fy = (dy / dist) * force;

                dispX[fromIndex] -= fx;
                dispY[fromIndex] -= fy;
                dispX[toIndex] += fx;
                dispY[toIndex] += fy;
            }

            // Move nodes and clamp to panel bounds.
            for (int i = 0; i < nodeCount; i++) {
                DisplayNode node = nodes.get(i);
                float dx = dispX[i];
                float dy = dispY[i];
                float disp = (float) Math.sqrt(dx * dx + dy * dy);

                if (disp > 0f) {
                    float limited = Math.min(disp, temperature);
                    node.x += (dx / disp) * limited;
                    node.y += (dy / disp) * limited;
                }

                node.x = Math.max(minX, Math.min(maxX, node.x));
                node.y = Math.max(minY, Math.min(maxY, node.y));
            }

            temperature *= 0.95f;
        }
    }

    void update() {
        setRoads(gameLogic.getRoads().get(0));
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.GREEN);
        for (DisplayEdge edge : edges) {
            DisplayNode from = edge.from;
            DisplayNode to = edge.to;
            List<Lane> lanes = edge.segment.getLanes();
            
            // Vector from start to end
            float dx = to.x - from.x;
            float dy = to.y - from.y;
            float dist = (float) Math.sqrt(dx * dx + dy * dy);
            
            if (dist < 0.01f) continue; // Skip if nodes are too close
            
            // Perpendicular vector (rotated 90 degrees)
            float perpX = -dy / dist;
            float perpY = dx / dist;
            
            // Spacing between lanes
            float laneSpacing = 3f; // Adjust as needed
            float totalWidth = (lanes.size() - 1) * laneSpacing;
            float startOffset = -totalWidth / 2f;
            
            // Draw each lane as a parallel line
            for (int i = 0; i < lanes.size(); i++) {
                float offset = startOffset + i * laneSpacing;
                
                int x1 = (int)(from.x + perpX * offset);
                int y1 = (int)(from.y + perpY * offset);
                int x2 = (int)(to.x + perpX * offset);
                int y2 = (int)(to.y + perpY * offset);
                
                g.drawLine(x1, y1, x2, y2);
            }
        }
        g.setColor(Color.ORANGE);
        for (DisplayNode node : nodes) {
            g.fillOval((int)(node.x - 5), (int)(node.y - 5), 10, 10);
        }
    }
}
