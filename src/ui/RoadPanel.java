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
import java.awt.Polygon;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.ToolTipManager;
import utils.Logger;

public class RoadPanel extends JPanel {
    class DisplayNode {
        float x;
        float y;
        Node node;
        public DisplayNode(float x, float y, Node node) {
            this.x = x;
            this.y = y;
            this.node = node;
        }
        public void render(java.awt.Graphics2D g2) {
            if (node instanceof Apartment) {
                g2.drawImage(apartmentImage, (int)(x - NODE_OFFSET), (int)(y - NODE_OFFSET), NODE_SIZE, NODE_SIZE, null);
            } else if (node instanceof Workplace) {
                g2.drawImage(workPlaceImage, (int)(x - NODE_OFFSET), (int)(y - NODE_OFFSET), NODE_SIZE, NODE_SIZE, null);
            } else if (node instanceof BusStop) {
                g2.drawImage(busStopImage, (int)(x - NODE_OFFSET), (int)(y - NODE_OFFSET), NODE_SIZE, NODE_SIZE, null);
            } else {
                g2.drawImage(nodeImage, (int)(x - NODE_OFFSET), (int)(y - NODE_OFFSET), NODE_SIZE, NODE_SIZE, null);
            }
        }
    }

    class DisplayEdge {
        DisplayNode start;
        DisplayNode end;
        RoadSegment segment;
        public DisplayEdge(DisplayNode from, DisplayNode to, RoadSegment segment) {
            this.start = from;
            this.end = to;
            this.segment = segment;
        }
        public void render(java.awt.Graphics2D g2) {
            List<Lane> lanes = segment.getLanes();

            // Vector from start to end
            float dx = end.x - start.x;
            float dy = end.y - start.y;
            float dist = (float) Math.sqrt(dx * dx + dy * dy);

            if (dist < 0.01f) return; // Skip if nodes are too close

            // Perpendicular vector (rotated 90 degrees)
            float perpX = -dy / dist;
            float perpY = dx / dist;

            // spacing between lane centers includes lane width + separator
            float laneSpacing = laneWidth + separatorWidth;
            float totalCenterSpan = (lanes.size() - 1) * laneSpacing;
            float startOffset = -totalCenterSpan / 2f;

            // Precompute center offsets for lanes so lane 0 is the lowest Y and higher indices have higher Y
            float[] centerOffsets = new float[lanes.size()];
            if (!lanes.isEmpty()) {
                if (perpY >= 0f) {
                    for (int i = 0; i < lanes.size(); i++) {
                        centerOffsets[i] = startOffset + i * laneSpacing;
                    }
                } else {
                    for (int i = 0; i < lanes.size(); i++) {
                        centerOffsets[i] = startOffset + (lanes.size() - 1 - i) * laneSpacing;
                    }
                }
            }

            // Draw each lane as a filled polygon (strip)
            for (int i = 0; i < lanes.size(); i++) {
                float centerOffset = centerOffsets[i];
                float leftOffset = centerOffset - laneWidth / 2f;
                float rightOffset = centerOffset + laneWidth / 2f;

                int[] xs = new int[4];
                int[] ys = new int[4];
                xs[0] = (int) (start.x + perpX * leftOffset);
                ys[0] = (int) (start.y + perpY * leftOffset);
                xs[1] = (int) (end.x + perpX * leftOffset);
                ys[1] = (int) (end.y + perpY * leftOffset);
                xs[2] = (int) (end.x + perpX * rightOffset);
                ys[2] = (int) (end.y + perpY * rightOffset);
                xs[3] = (int) (start.x + perpX * rightOffset);
                ys[3] = (int) (start.y + perpY * rightOffset);

                g2.setColor(laneColor);
                g2.fillPolygon(xs, ys, 4);
                g2.setColor(laneBorderColor);
                g2.drawPolygon(xs, ys, 4);
            }

            // Draw separators between lanes
            g2.setColor(separatorColor);
            for (int i = 0; i < Math.max(0, lanes.size() - 1); i++) {
                float sepCenter = (centerOffsets[i] + centerOffsets[i + 1]) * 0.5f;
                float sepLeft = sepCenter - separatorWidth / 2f;
                float sepRight = sepCenter + separatorWidth / 2f;

                int[] xs = new int[4];
                int[] ys = new int[4];
                xs[0] = (int) (start.x + perpX * sepLeft);
                ys[0] = (int) (start.y + perpY * sepLeft);
                xs[1] = (int) (end.x + perpX * sepLeft);
                ys[1] = (int) (end.y + perpY * sepLeft);
                xs[2] = (int) (end.x + perpX * sepRight);
                ys[2] = (int) (end.y + perpY * sepRight);
                xs[3] = (int) (start.x + perpX * sepRight);
                ys[3] = (int) (start.y + perpY * sepRight);

                g2.fillPolygon(xs, ys, 4);
            }

            // Draw vehicles on top of lanes after all lanes and separators are rendered
            for (int i = 0; i < lanes.size(); i++) {
                Lane lane = lanes.get(i);
                float centerOffset = centerOffsets[i];
                renderVehiclesOnLane(g2, lane, centerOffset, perpX, perpY);
            }
        }

        public Lane hitTestLane(int mx, int my) {
            List<Lane> lanes = segment.getLanes();

            float dx = end.x - start.x;
            float dy = end.y - start.y;
            float dist = (float) Math.sqrt(dx * dx + dy * dy);
            if (dist < 0.01f) return null;

            float perpX = -dy / dist;
            float perpY = dx / dist;

            float laneSpacing = laneWidth + separatorWidth;
            float totalCenterSpan = (lanes.size() - 1) * laneSpacing;
            float startOffset = -totalCenterSpan / 2f;

            float[] centerOffsets = new float[lanes.size()];
            if (!lanes.isEmpty()) {
                if (perpY >= 0f) {
                    for (int i = 0; i < lanes.size(); i++) {
                        centerOffsets[i] = startOffset + i * laneSpacing;
                    }
                } else {
                    for (int i = 0; i < lanes.size(); i++) {
                        centerOffsets[i] = startOffset + (lanes.size() - 1 - i) * laneSpacing;
                    }
                }
            }

            for (int i = 0; i < lanes.size(); i++) {
                float centerOffset = centerOffsets[i];
                float leftOffset = centerOffset - laneWidth / 2f;
                float rightOffset = centerOffset + laneWidth / 2f;

                int[] xs = new int[4];
                int[] ys = new int[4];
                xs[0] = (int) (start.x + perpX * leftOffset);
                ys[0] = (int) (start.y + perpY * leftOffset);
                xs[1] = (int) (end.x + perpX * leftOffset);
                ys[1] = (int) (end.y + perpY * leftOffset);
                xs[2] = (int) (end.x + perpX * rightOffset);
                ys[2] = (int) (end.y + perpY * rightOffset);
                xs[3] = (int) (start.x + perpX * rightOffset);
                ys[3] = (int) (start.y + perpY * rightOffset);

                Polygon poly = new Polygon(xs, ys, 4);
                if (poly.contains(mx, my)) {
                    return lanes.get(i);
                }
            }

            return null;
        }

        private void renderVehiclesOnLane(java.awt.Graphics2D g2, Lane lane, float centerOffset, float perpX, float perpY) {
            List<gamelogic.Vehicle> vehicles = lane.getVehicles();
            if (vehicles.isEmpty()) return;

            // Vector from start to end
            float dx = end.x - start.x;
            float dy = end.y - start.y;
            float segmentLength = (float) Math.sqrt(dx * dx + dy * dy);
            float dirX = dx / segmentLength;
            float dirY = dy / segmentLength;

            // Calculate positions along the lane for each vehicle
            for (int i = 0; i < vehicles.size(); i++) {
                gamelogic.Vehicle vehicle = vehicles.get(i);

                // Position along lane: center if single vehicle, evenly distributed if multiple
                float positionAlongLane;
                if (vehicles.size() == 1) {
                    positionAlongLane = 0.5f; // Middle of lane
                } else {
                    positionAlongLane = (float) i / (vehicles.size() - 1); // Evenly distribute
                }

                // Calculate vehicle position on the lane
                float vehicleX = start.x + dirX * segmentLength * positionAlongLane + perpX * centerOffset;
                float vehicleY = start.y + dirY * segmentLength * positionAlongLane + perpY * centerOffset;

                // Draw vehicle sprite based on type
                if (vehicle instanceof gamelogic.SnowPlow) {
                    if (snowPlowImage != null) {
                        g2.drawImage(vehiclePanel.getSelectedVehicle() == vehicle ? snowPlowSelectedImage : snowPlowImage, (int)(vehicleX - VEHICLE_OFFSET), (int)(vehicleY - VEHICLE_OFFSET), VEHICLE_SIZE, VEHICLE_SIZE, null);
                    }
                } else if (vehicle instanceof gamelogic.Bus) {
                    if (busImage != null) {
                        g2.drawImage(vehiclePanel.getSelectedVehicle() == vehicle ? busSelectedImage : busImage, (int)(vehicleX - VEHICLE_OFFSET), (int)(vehicleY - VEHICLE_OFFSET), VEHICLE_SIZE, VEHICLE_SIZE, null);
                    }
                } else if (vehicle instanceof gamelogic.Car) {
                    if (carImage != null) {
                        g2.drawImage(carImage, (int)(vehicleX - VEHICLE_OFFSET), (int)(vehicleY - VEHICLE_OFFSET), VEHICLE_SIZE, VEHICLE_SIZE, null);
                    }
                }
            }
        }
    }

    private transient BufferedImage snowPlowImage;
    private transient BufferedImage snowPlowSelectedImage;
    private transient BufferedImage busImage;
    private transient BufferedImage busSelectedImage;
    private transient BufferedImage carImage;
    private transient BufferedImage apartmentImage;
    private transient BufferedImage busStopImage;
    private transient BufferedImage nodeImage;
    private transient BufferedImage workPlaceImage;

    private static final int NODE_SIZE = 40;
    private static final int NODE_OFFSET = NODE_SIZE / 2;

    private static final int VEHICLE_SIZE = 30;
    private static final int VEHICLE_OFFSET = VEHICLE_SIZE / 2;

    // Configurable lane rendering
    private float laneWidth = 8f; // pixels
    private float separatorWidth = 2f; // pixels between lanes
    private Color laneColor = new Color(60, 60, 60);
    private Color separatorColor = Color.WHITE;
    private Color laneBorderColor = Color.BLACK;



    private transient List<DisplayNode> nodes = new ArrayList<>();
    private transient List<DisplayEdge> edges = new ArrayList<>();

    private final GameLogic gameLogic;
    private final GamePanel gamePanel;
    private final Random random = new Random();
    private final VehiclePanel vehiclePanel;

    public RoadPanel(GameLogic gameLogic, GamePanel gamePanel, VehiclePanel vehiclePanel) {
        setBackground(UIStyles.backgroundColor);
        this.gameLogic = gameLogic;
        this.gamePanel = gamePanel;
        this.vehiclePanel = vehiclePanel;
        gameLogic.addGameStateChangeListener(this::updateDisplay);
        gameLogic.addTopologyChangedListener(() -> {
            recalculateRoads();
            updateDisplay();
        });

        vehiclePanel.addSelectionChangeListener(this::updateDisplay);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                recalculateRoads();
                updateDisplay();
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleClick(e.getX(), e.getY());
            }
        });

        // Show lane inspect text as tooltip when hovering
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                Lane hoverLane = null;
                for (DisplayEdge de : edges) {
                    Lane l = de.hitTestLane(e.getX(), e.getY());
                    if (l != null) { hoverLane = l; break; }
                }

                if (hoverLane != null) {
                    try {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Lane ").append(hoverLane.getId()).append(": ");
                        sb.append("Snow=").append(hoverLane.getSnow()).append(", ");
                        sb.append("Gravel=").append(hoverLane.getGravel()).append(", ");
                        sb.append("Icing=").append(hoverLane.getIcingProgress()).append(", ");
                        sb.append("Iced=").append(hoverLane.isIced() ? "yes" : "no").append(", ");
                        sb.append("Blocked=").append(hoverLane.isBlocked() ? "yes" : "no").append(", ");
                        sb.append("Vehicles=").append(hoverLane.getVehicles().size());
                        setToolTipText(sb.toString());
                    } catch (Exception ex) {
                        setToolTipText(null);
                    }
                } else {
                    setToolTipText(null);
                }
                // Notify ToolTipManager to update immediately
                ToolTipManager.sharedInstance().mouseMoved(e);
            }
        });

        loadImages();
        // Register for tooltips and show immediately
        ToolTipManager.sharedInstance().registerComponent(this);
        ToolTipManager.sharedInstance().setInitialDelay(0);
    }

    @Override
    public String getToolTipText(MouseEvent event) {
        if (event == null) return null;
        for (DisplayEdge de : edges) {
            Lane l = de.hitTestLane(event.getX(), event.getY());
            if (l != null) {
                try {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Lane ").append(l.getId()).append(": ");
                    sb.append("Snow=").append(l.getSnow()).append(", ");
                    sb.append("Gravel=").append(l.getGravel()).append(", ");
                    sb.append("Icing=").append(l.getIcingProgress()).append(", ");
                    sb.append("Iced=").append(l.isIced() ? "yes" : "no").append(", ");
                    sb.append("Blocked=").append(l.isBlocked() ? "yes" : "no").append(", ");
                    sb.append("Vehicles=").append(l.getVehicles().size());
                    return sb.toString();
                } catch (Exception ex) {
                    return null;
                }
            }
        }
        return null;
    }

    private void updateDisplay() {
        repaint();
    }

    private void handleClick(int mx, int my) {
        // Check nodes first (clicking a node takes precedence)
        for (DisplayNode dn : nodes) {
            float dx = mx - dn.x;
            float dy = my - dn.y;
            if (dx * dx + dy * dy <= NODE_OFFSET * NODE_OFFSET) {
                // Log node id
                try {
                    Logger.logLine("NODE [" + dn.node.getId() + "] CLICKED");
                    gamePanel.nodeClicked(dn.node);
                } catch (Exception ex) {
                    Logger.logLine("NODE clicked");
                }
                return;
            }
        }

        // Then check lanes
        for (DisplayEdge de : edges) {
            Lane lane = de.hitTestLane(mx, my);
            if (lane != null) {
                try {
                    Logger.logLine("LANE [" + lane.getId() + "] CLICKED");
                    gamePanel.laneClicked(lane);
                    
                } catch (Exception ex) {
                    Logger.logLine("LANE clicked");
                }
                return;
            }
        }
    }

    private void loadImages() {
        try {
            snowPlowImage = ImageIO.read(new File("resources/sprites/Snowplow.png"));
            snowPlowSelectedImage = ImageIO.read(new File("resources/sprites/Snowplow_Selected.png"));
            carImage = ImageIO.read(new File("resources/sprites/Car.png"));
            busImage = ImageIO.read(new File("resources/sprites/Bus.png"));
            busSelectedImage = ImageIO.read(new File("resources/sprites/Bus_Selected.png"));
            apartmentImage = ImageIO.read(new File("resources/sprites/Apartment.png"));
            busStopImage = ImageIO.read(new File("resources/sprites/BusStop.png"));
            nodeImage = ImageIO.read(new File("resources/sprites/Node.png"));
            workPlaceImage = ImageIO.read(new File("resources/sprites/WorkPlace.png"));
        } catch (IOException e) {
            Logger.logError("Error loading images: " + e.getMessage());
        }
    }

    public void recalculateRoads() {
        nodes.clear();
        edges.clear();

        RoadNetwork roads = gameLogic.getRoads();
        if (roads == null) {
            return;
        }

        Map<Node, DisplayNode> nodeMap = new HashMap<>();
 
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
                Integer fromIndex = nodeIndex.get(edge.start);
                Integer toIndex = nodeIndex.get(edge.end);
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

                node.x = Math.clamp(node.x, minX, maxX);
                node.y = Math.clamp(node.y, minY, maxY);
            }

            temperature *= 0.95f;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // draw roads (lanes as filled strips with separators)
        java.awt.Graphics2D g2 = (java.awt.Graphics2D) g;
        g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        for (DisplayEdge edge : edges) {
            edge.render(g2);
        }
                

        g2.setColor(Color.ORANGE);
        for (DisplayNode node : nodes) {
            node.render(g2);
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
