import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.swing.JPanel;

public class RoadPanel extends JPanel {
    class DisplayNode {
        int x, y;
        public DisplayNode(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    class DisplayEdge {
        DisplayNode from, to;
        public DisplayEdge(DisplayNode from, DisplayNode to) {
            this.from = from;
            this.to = to;
        }
    }

    private List<DisplayNode> nodes = new ArrayList<>();
    private List<DisplayEdge> edges = new ArrayList<>();

    private GameLogic gameLogic;

    public RoadPanel(GameLogic gameLogic) {
        setBackground(UIStyles.backgroundColor);
        this.gameLogic = gameLogic;
        gameLogic.addGameStateChangeListener(this::update);
    }

    public void setRoads(RoadNetwork roads) {
        nodes.clear();
        edges.clear();

        Map<Node, DisplayNode> nodeMap = new HashMap<>();
        Random rand = new Random();
        for (Node node : roads.getNodes()) {
            DisplayNode displayNode = new DisplayNode(rand.nextInt(800), rand.nextInt(400));
            nodes.add(displayNode);
            nodeMap.put(node, displayNode);
        }

        for (RoadSegment segment : roads.getRoadSegments()) {
            DisplayNode from = nodeMap.get(segment.getStartPoint());
            DisplayNode to = nodeMap.get(segment.getEndPoint());
            if (from != null && to != null) {
                edges.add(new DisplayEdge(from, to));
            }
        }
    }

    void update() {
        repaint();
        setRoads(gameLogic.getRoads().get(0));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.GREEN);
        for (DisplayEdge edge : edges) {
            g.drawLine(edge.from.x, edge.from.y, edge.to.x, edge.to.y);
        }
        g.setColor(Color.ORANGE);
        for (DisplayNode node : nodes) {
            g.fillOval(node.x - 5, node.y - 5, 10, 10);
        }
    }
}
