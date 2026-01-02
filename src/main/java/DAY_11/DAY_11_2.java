package DAY_11;

import Reader.FileReader;
import Reader.InputType;
import Utils.ResultLogger;
import Utils.Timer;

import java.util.*;

public class DAY_11_2 {

    private static final String DAY = "11";
    private static final String PART = "_2";

    public static void main(String[] args) throws Exception {
        Timer.startTimer();
        String[] input = FileReader.readFileAsString(DAY, InputType.NORMAL).split("[\\r\\n]+");

        Set<Node> nodes = new HashSet<>();
        Map<String, Node> nodesMap = new HashMap<>();
        for (String str : input) {
            System.out.println(str);
            nodes.add(new Node(str));
        }

        System.out.println("=== MAKE CONNECTIONS MAP ===");
        for (Node node : nodes) {
            nodesMap.put(node.getId(), node);
            System.out.println(node);
        }

        System.out.println("=== MAKE CONNECTIONS ===");
        for (Node node : nodes) {
            node.makeRealConnections(nodesMap);
        }

        System.out.println("=== PRINT ===");
        for (Node node : nodes) {
            // nodesMap.put(node.getId(), node);
            //System.out.println(node);
        }

        long result = 0;

        Node startNode = nodesMap.get("svr");
        Node endNode = nodesMap.get("out");

        result = countPaths(startNode, endNode);
        System.out.println("RESULT: " + result);
        Timer.printEndTime();

        // Log the result to file
        long executionTime = Timer.getExecutionTimeNanos();
        ResultLogger.logResult(PART, DAY, executionTime, String.valueOf(result));
    }

    public static long countPaths(Node start, Node target) {
        return dfs(start, target, false, false, new HashMap<>());
    }

    private static long dfs(
            Node current,
            Node target,
            boolean seenDac,
            boolean seenFft,
            Map<State, Long> memo
    ) {
        if (current.getId().equals("dac")) seenDac = true;
        if (current.getId().equals("fft")) seenFft = true;

        if (current.equals(target)) {
            //return 1;
            return (seenDac && seenFft) ? 1 : 0;
        }

        State state = new State(current, seenDac, seenFft);
        Long cached = memo.get(state);
        if (cached != null) {
            return cached;
        }

        long paths = 0;
        for (Node next : current.getConnections()) {
            paths += dfs(next, target, seenDac, seenFft, memo);
        }

        memo.put(state, paths);
        return paths;
    }

    record State(Node node, boolean dac, boolean fft) {
    }

    public static class Node {
        String id;
        List<String> stringConnections = new ArrayList<>();
        List<Node> connections = new ArrayList<>();

        public Node(String line) {
            String[] divided = line.split(":");
            this.id = divided[0];

            if (divided.length <= 1) {
                return;
            }
            String[] connections = divided[1].trim().split(" ");
            for (String connection : connections) {
                stringConnections.add(connection);
            }
        }

        public String getId() {
            return id;
        }

        public List<String> getStringConnections() {
            return stringConnections;
        }

        public List<Node> getConnections() {
            return connections;
        }

        public void makeRealConnections(Map<String, Node> nodesMap) {
            for (String stringConnection : stringConnections) {
                connections.add(nodesMap.get(stringConnection));
            }
        }


        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return Objects.equals(id, node.id);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(id);
        }

        @Override
        public String toString() {
            return "Node{" +
                    "id='" + id + '\'' +
                    ", stringConnections=" + stringConnections +
                    ", connections=" + connections +
                    '}';
        }
    }

}
