package tree.binary

class Node {
    Node left
    Node right
    int value
    def boolean equals(Node n){
        return this.value==n.value
    }
}
