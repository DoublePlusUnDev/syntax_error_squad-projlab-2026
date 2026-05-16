package ui;
import gamelogic.Apartment;
import gamelogic.BusStop;
import gamelogic.GameLogic;
import gamelogic.Lane;
import gamelogic.Node;
import gamelogic.RoadNetwork;
import gamelogic.RoadSegment;
import gamelogic.Workplace;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.imageio.ImageIO;
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

    private BufferedImage snowPlowImage;
    private BufferedImage busImage;
    private BufferedImage carImage;
    private BufferedImage apartmentImage;
    private BufferedImage busStopImage;
    private BufferedImage nodeImage;
    private BufferedImage workPlaceImage;

    private int NODE_SIZE = 40;
    private int NODE_OFFSET = NODE_SIZE / 2;

    // Configurable lane rendering
    private float laneWidth = 8f; // pixels
    private float separatorWidth = 2f; // pixels between lanes
    private Color laneColor = new Color(60, 60, 60);
    private Color separatorColor = Color.WHITE;
    private Color laneBorderColor = Color.BLACK;



    private transient List<DisplayNode> nodes = new ArrayList<>();
    private transient List<DisplayEdge> edges = new ArrayList<>();

    private transient GameLogic gameLogic;

    public RoadPanel(GameLogic gameLogic) {
        setBackground(UIStyles.backgroundColor);
        this.gameLogic = gameLogic;
        gameLogic.addGameStateChangeListener(this::updateDisplay);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (gameLogic.getRoads() == null) 
                    return;
                
                setRoads(gameLogic.getRoads());
                repaint();
            }
        });

        loadImages();
    }

    private void loadImages() {
        try {
            snowPlowImage = ImageIO.read(new File("resources/sprites/Snowplow.png"));
            carImage = ImageIO.read(new File("resources/sprites/Car.png"));
            busImage = ImageIO.read(new File("resources/sprites/Bus.png"));
            apartmentImage = ImageIO.read(new File("resources/sprites/Apartment.png"));
            busStopImage = ImageIO.read(new File("resources/sprites/BusStop.png"));
            nodeImage = ImageIO.read(new File("resources/sprites/Node.png"));
            workPlaceImage = ImageIO.read(new File("resources/sprites/WorkPlace.png"));
        } catch (Exception e) {
            System.err.println("Error loading images: " + e.getMessage());
        }
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

    private void updateDisplay() {
        setRoads(gameLogic.getRoads());
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // draw roads (lanes as filled strips with separators)
        java.awt.Graphics2D g2 = (java.awt.Graphics2D) g;
        g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
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

            // spacing between lane centers includes lane width + separator
            float laneSpacing = laneWidth + separatorWidth;
            float totalCenterSpan = (lanes.size() - 1) * laneSpacing;
            float startOffset = -totalCenterSpan / 2f;

            // Draw each lane as a filled polygon (strip)
            for (int i = 0; i < lanes.size(); i++) {
                float centerOffset = startOffset + i * laneSpacing;
                float leftOffset = centerOffset - laneWidth / 2f;
                float rightOffset = centerOffset + laneWidth / 2f;

                int[] xs = new int[4];
                int[] ys = new int[4];
                xs[0] = (int) (from.x + perpX * leftOffset);
                ys[0] = (int) (from.y + perpY * leftOffset);
                xs[1] = (int) (to.x + perpX * leftOffset);
                ys[1] = (int) (to.y + perpY * leftOffset);
                xs[2] = (int) (to.x + perpX * rightOffset);
                ys[2] = (int) (to.y + perpY * rightOffset);
                xs[3] = (int) (from.x + perpX * rightOffset);
                ys[3] = (int) (from.y + perpY * rightOffset);

                g2.setColor(laneColor);
                g2.fillPolygon(xs, ys, 4);
                g2.setColor(laneBorderColor);
                g2.drawPolygon(xs, ys, 4);
            }

            // Draw separators between lanes
            g2.setColor(separatorColor);
            for (int i = 0; i < Math.max(0, lanes.size() - 1); i++) {
                float sepCenter = startOffset + (i + 0.5f) * laneSpacing;
                float sepLeft = sepCenter - separatorWidth / 2f;
                float sepRight = sepCenter + separatorWidth / 2f;

                int[] xs = new int[4];
                int[] ys = new int[4];
                xs[0] = (int) (from.x + perpX * sepLeft);
                ys[0] = (int) (from.y + perpY * sepLeft);
                xs[1] = (int) (to.x + perpX * sepLeft);
                ys[1] = (int) (to.y + perpY * sepLeft);
                xs[2] = (int) (to.x + perpX * sepRight);
                ys[2] = (int) (to.y + perpY * sepRight);
                xs[3] = (int) (from.x + perpX * sepRight);
                ys[3] = (int) (from.y + perpY * sepRight);

                g2.fillPolygon(xs, ys, 4);
            }
        }
                

        g2.setColor(Color.ORANGE);
        for (DisplayNode node : nodes) {
            if (node.node instanceof Apartment) {
                g2.drawImage(apartmentImage, (int)(node.x - NODE_OFFSET), (int)(node.y - NODE_OFFSET), NODE_SIZE, NODE_SIZE, null);
            } else if (node.node instanceof Workplace) {
                g2.drawImage(workPlaceImage, (int)(node.x - NODE_OFFSET), (int)(node.y - NODE_OFFSET), NODE_SIZE, NODE_SIZE, null);
            } else if (node.node instanceof BusStop) {
                g2.drawImage(busStopImage, (int)(node.x - NODE_OFFSET), (int)(node.y - NODE_OFFSET), NODE_SIZE, NODE_SIZE, null);
            } else {
                g2.drawImage(nodeImage, (int)(node.x - NODE_OFFSET), (int)(node.y - NODE_OFFSET), NODE_SIZE, NODE_SIZE, null);
            }
        }
    }
            
    // Config setters
    public void setLaneWidth(float laneWidth) {
        this.laneWidth = Math.max(0f, laneWidth);
        repaint();
    }

    public void setSeparatorWidth(float separatorWidth) {
        this.separatorWidth = Math.max(0f, separatorWidth);
        repaint();
    }

    public void setLaneColor(Color laneColor) {
        if (laneColor != null) this.laneColor = laneColor;
        repaint();
    }

    public void setSeparatorColor(Color separatorColor) {
        if (separatorColor != null) this.separatorColor = separatorColor;
        repaint();
    }

    public void setLaneBorderColor(Color laneBorderColor) {
        if (laneBorderColor != null) this.laneBorderColor = laneBorderColor;
        repaint();
    }
}
