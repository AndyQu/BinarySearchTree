package tree.binary

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import stack.ThreadUtil

class CommonOperation {
    def static Logger LOGGER = null
    static{
        ThreadUtil.doInject()
        LOGGER = LoggerFactory.getLogger("bst");
        LOGGER.metaClass.invokeMethod={
            String methodName, args->
                if((['info'] as HashSet).contains(methodName)){
                    int count=Thread.currentThread().methodCountInStack(["removeNodeFromTree","removeNode"] as HashSet)
                    1.upto(count){args[0]="..."+args[0]}
                }
                /*
                 * getMetaMethod存在二义性，详见：https://github.com/AndyQu/groovy_pitfall/blob/master/getMetaMethod.groovy
                 * */
                def method=LOGGER.metaClass.getMetaMethod(methodName, [String.class, Object[].class] as Object[])
//                def method=LOGGER.metaClass.getMetaMethod(methodName, args)
                method.invoke(delegate, (String)args[0], (Object[])args[1..args.length-1])
        }
    }
    /**
     * the key point for writing codes nice and clean is the concept: "replace node", instead of "left biggest node"
     * suppose node A is the target node to be deleted.
     * If A has no child, then no replace node.
     * If A only has left child tree, then the replace node should be A's left child, we don't need to find the left biggest node.
     * If A only has right child tree, then the replace node should be A's right child.
     * If A has both left and right child trees, then the replace node should be A's left biggest node.
     *
     * So the replace node is not always the left biggest node.
     *
     * use the replace node to replace the deleted node, and use "parent" to link it.
     */
    
    /**
     * 从树treeRoot中，寻找与targetNode.value相同值的Node，从树中删除，并保持二叉搜索树的性质不变。
     * 返回树的根节点
     * @param treeRoot
     * @param targetNode
     * @return
     */
    def static Tuple removeNodeFromTree(Node treeRoot, Node targetNode){
        assert treeRoot!=null && targetNode!=null
        LOGGER.info("event=begin_removeNodeFromTree root={} target={}", treeRoot.value, targetNode.value)
        def (parent, realTargetNode)=find(treeRoot, targetNode)
        Node replaceNode = removeNode(parent, realTargetNode)
        if(parent==null){
            LOGGER.info("event=end_removeNodeFromTree root={} target={} newRoot={} replaceNode={}",
                treeRoot.value, targetNode.value, replaceNode?.value, replaceNode?.value);
            return new Tuple(replaceNode, replaceNode)
        }else{
            LOGGER.info("event=end_removeNodeFromTree root={} target={} newRoot={} replaceNode={}",
                treeRoot.value, targetNode.value, treeRoot.value, replaceNode?.value);
            return new Tuple(treeRoot,replaceNode)
        }
    }

    

    /**
     * 给定父节点、目标节点，将目标节点从树中删除，并保持二叉搜索树的性质
     * @param parent
     * @param targetNode
     * @return
     */
    def static Node removeNode(Node parent, Node targetNode){
        /*
         * Key Point:
         * 存在targetNode是root节点的情况
         * */
        assert parent==null||parent.left==targetNode||parent.right==targetNode
        Node replaceNode=null
        if(targetNode.left==null){
            replaceNode=targetNode.right
        }else if(targetNode.right==null){
            replaceNode=targetNode.left
        }else{
            /*
             * Key Point
             * 这段代码：与parent node 完全不相关
             * 这段代码：寻找左子树最右节点作为replaceNode，找到之后，又产生了类似的操作：寻找replaceNode、删除、替换。
             * 这一系列的操作恰恰是removeNodeFromTree()函数
             * 
             * */
            replaceNode = findRightMostNode(targetNode.left)
            removeNodeFromTree(targetNode.left, replaceNode)
            /*
             * Key Point:
             * 左子树可能只有一个节点
             * 此时，不能做replaceNode.left=targetNode.left，因为targetNode.left==replaceNode，会形成自循环
             * */
            if(replaceNode==targetNode.left){
                replaceNode.right=targetNode.right
            }else{
                replaceNode.right=targetNode.right
                replaceNode.left=targetNode.left
            }
        }
        if(parent==null){
            /*
             * Key Point:当targetNode是root的时候，则replaceNode为新的root
             * */
            replaceNode
        }else{
            if(parent.left==targetNode){
                parent.left=replaceNode
            }else{
                parent.right=replaceNode
            }
            replaceNode
        }
    }
    
    /**
     * 在树treeRoot中，寻找value与targetNode.value相同的Node及它的parent Node
     * @param treeRoot
     * @param targetNode
     * @return
     */
    def static find(Node treeRoot, Node targetNode){
        assert treeRoot!=null && targetNode!=null
        Node parent=null
        Node cursor=treeRoot
        while(true){
            if(cursor.value==targetNode.value){
                LOGGER.info("event=found_node_in_tree root={} parent={} target={}", treeRoot.value, parent?.value, cursor.value)
                return new Tuple(parent,cursor);
            }else if(cursor.value>targetNode.value){
                parent=cursor
                cursor=cursor.left
            }else{
                parent=cursor
                cursor=cursor.right
            }
        }
    }

    /**
     * 找到从节点n开始的最右节点
     * @param n
     * @return
     */
    def static Node findRightMostNode(Node n){
        assert n!=null
        while(n.right!=null){
            n=n.right
        }
        return n
    }
}
