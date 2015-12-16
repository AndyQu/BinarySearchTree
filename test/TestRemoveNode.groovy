import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

import tree.binary.CommonOperation
import tree.binary.Node

import org.testng.Assert

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
                            value:15,
                            left:new Node(
                                    value:11,
                                    right:new Node(
                                            value:12,
                                            right:new Node(
                                                    value:13
                                                )
                                        )
                                ),
                            right:new Node(
                                    value:16
                                )
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
        Assert.assertEquals(newRoot.value, 16)
    }
    
    @Test
    def void test_removeNodeWithOnlyLeftChild(){
        def (newRoot,replaceNode)=CommonOperation.removeNodeFromTree(root,new Node(value:30))
        Assert.assertEquals(newRoot.value,20)
        Assert.assertEquals(replaceNode.value,25)
    }
    
    @Test
    def void test_removeNodeWithOnlyRightChild(){
        def (newRoot, replaceNode)=CommonOperation.removeNodeFromTree(root, new Node(value:25))
        Assert.assertEquals(newRoot.value,20)
        Assert.assertEquals(replaceNode.value, 27)
    }
    
    /**
     * 被删除的节点10，左右子树都有，而且其left child没有右子树
     */
    @Test
    def void test_removeNodeWithTwoChilds(){
        def (newRoot, replaceNode)=CommonOperation.removeNodeFromTree(root,new Node(value:10))
        Assert.assertEquals(newRoot.value,20)
        Assert.assertEquals(replaceNode.value, 5)
    }
    
    /**
     * 被删除的节点15，左右子树都有，而且其left child有右子树
     */
    @Test
    def void test_removeNodeWithLeftChildHavingRightTree(){
        def (newRoot, replaceNode)=CommonOperation.removeNodeFromTree(root,new Node(value:15))
        Assert.assertEquals(newRoot.value,20)
        Assert.assertEquals(replaceNode.value, 13)
    }
}