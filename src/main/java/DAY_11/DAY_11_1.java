package DAY_11;

import Reader.FileReader;
import Reader.InputType;
import Utils.ResultLogger;
import Utils.Timer;

import java.util.*;

public class DAY_11_1 {

    private static final String DAY = "11";
    private static final String PART = "_1";

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

        Node startNode = nodesMap.get("you");
        Node endNode = nodesMap.get("out");

        int result = 0;
        result = countPaths(startNode, endNode);

        System.out.println("RESULT: " + result);
        Timer.printEndTime();

        // Log the result to file
        long executionTime = Timer.getExecutionTimeNanos();
        ResultLogger.logResult(PART, DAY, executionTime, String.valueOf(result));
    }

    public static int countPaths(Node start, Node target) {
        return dfs(start, target, new HashSet<>());
    }

    private static int dfs(Node current, Node target, Set<Node> visited) {
        if (current.equals(target)) {
            return 1;
        }

        visited.add(current);
        int paths = 0;

        for (Node next : current.connections) {
            if (!visited.contains(next)) {
                paths += dfs(next, target, visited);
            }
        }

        visited.remove(current);
        return paths;
    }


    public static int getAllPaths(Set<Node> nodes, Map<String, Node> nodesMap) {
        Node startNode = nodesMap.get("you");
        Node endNode = nodesMap.get("out");
        int counter = 0;
        List<Stack<Node>> nodesList = new ArrayList<>();
        Stack<Node> startStack = new Stack<>();
        startStack.add(startNode);
        nodesList.add(startStack);

        while (true) {

            List<Stack<Node>> nodesToAdd = new ArrayList<>();
            List<Stack<Node>> nodesToRemove = new ArrayList<>();

            for (Stack<Node> nodeee : nodesList) {
                Node peek = nodeee.peek();
                for (Node node : peek.connections) {
                    if (node.getId().equals("out")) {
                        counter++;
                        nodesToRemove.add(nodeee);
                    }

                    if (!nodeee.contains(node)) {
                        Stack<Node> newStack = (Stack<Node>) nodeee.clone();
                        newStack.add(node);
                        nodesToAdd.add(newStack);
                        nodesToRemove.add(nodeee);

                    } else {
                        nodesToRemove.add(nodeee);

                    }
                }
            }
            nodesList.removeAll(nodesToRemove);
            nodesList.addAll(nodesToAdd);

            if (nodesToAdd.isEmpty()) {
                return counter;
            }


        }

        //return counter;
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
