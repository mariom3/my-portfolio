/*
    Mario Morales
    Queue
    6 April 2018
*/

public class AVLTree {

    static class Node {

        private CTAStation station;
        private Node left;
        private Node right;

        Node(CTAStation station){
          this.station = station;
        }

        Node(CTAStation station, Node left, Node right){
          this.station = station;
          this.left = left;
          this.right = right;
        }
    }

    private int size;
    private Node root;

    public AVLTree(){
        this.size = 0;
        this.root = null;
    }

    public void add(CTAStation station){
      this.root = addStation(this.root, station);
    }

    private Node addStation(Node node, CTAStation station){
      if (node == null)
        return new AVLTree.Node(station);
      else if (station.index > node.station.index)
        node.right = addStation(node.right, station);
      else if (station.index < node.station.index)
        node.left = addStation(node.left, station);
      return node;
    }

    public void print(){
      print_value(this.root);
    }

    private void print_value(Node node){
      if (node != null) {
        print_value(node.left);
        System.out.println(node.station.getName());
        print_value(node.right);
      }
    }

}
