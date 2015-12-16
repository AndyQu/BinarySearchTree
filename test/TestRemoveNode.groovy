import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test
import tree.binary.CommonOperation
import tree.binary.Node

class TestRemoveNode{
    Node root
    @BeforeMethod
    def void  setUp() {
        root=new Node(
            value:20,
            left:new Node(
                    value:10,
                    left:new Node(
                            value:5
                        ),
                    right:new Node(
                            value:15
                        )
                ),
            right:new Node(
                    value:30,
                    left:new Node(
                            value:25,
                            right:new Node(
                                    value:27
                                )
                        )
                )
        )
    }
    @Test
    def void test_removeRoot(){
        def( newRoot,replaceNode)=CommonOperation.removeNodeFromTree(root, new Node(value:20))
        println newRoot.value
        assert newRoot.value==15
    }
    
    @Test
    def void test_removeNodeWithOnlyLeftChild(){
        def (newRoot,replaceNode)=CommonOperation.removeNodeFromTree(root,new Node(value:30))
        assert newRoot.value==20
        assert replaceNode.value==25
    }
    
    @Test
    def void test_removeNodeWithOnlyRightChild(){
        def (newRoot, replaceNode)=CommonOperation.removeNodeFromTree(root, new Node(value:25))
        assert newRoot.value==20
        assert replaceNode.value==27
    }
}