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
                            value:5,
                            left:null,
                            right:null
                        ),
                    right:new Node(
                            value:15,
                            left:null,
                            right:null
                        )
                ),
            right:new Node(
                    value:30,
                    left:null,
                    right:null
                )
        )
    }
    @Test
    def void test_removeRoot(){
        setUp()
        Node newRoot=CommonOperation.removeNodeFromTree(root, new Node(value:20))
        println newRoot.value
        assert newRoot.value==15
    }
}