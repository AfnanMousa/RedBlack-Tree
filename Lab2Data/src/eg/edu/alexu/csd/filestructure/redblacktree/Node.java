package eg.edu.alexu.csd.filestructure.redblacktree;

public class Node implements INode{

    private INode leftChild;
    private INode rightChild;
    private INode parent;
    private Comparable key= null;
    private Object value= null;
    private boolean color = INode.BLACK;
    Node (){
    }
    Node (INode current){
        this.leftChild=current.getLeftChild();
        this.rightChild=current.getRightChild();
        this.parent=current.getParent();
        this.key = current.getKey();
        this.value = current.getValue();
        this.color = current.getColor();
    }
/*  Node (/*Node current, Node leftChild,Node rightChild,Node parent,Comparable key, Object value/*, boolean color){
        /*this.current=current;
        this.leftChild=leftChild;
        this.rightChild=rightChild;
        this.parent=parent;
        this.key = key;
        this.value = value;
        this.color = color;
    }*/

    @Override
    public void setParent(INode parent) {
        this.parent = parent;
    }

    @Override
    public INode getParent() {
        return this.parent;
    }

    @Override
    public void setLeftChild(INode leftChild) {
        this.leftChild = leftChild;
    }

    @Override
    public INode getLeftChild() {
        return this.leftChild;
    }

    @Override
    public void setRightChild(INode rightChild) {
        this.rightChild = rightChild;
    }

    @Override
    public INode getRightChild() {
        return this.rightChild;
    }

    @Override
    public Comparable getKey() {
        return this.key;
    }

    @Override
    public void setKey(Comparable key) {
        this.key = key;
    }

    @Override
    public Object getValue() {
        return this.value;
    }

    @Override
    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public boolean getColor() {
        return this.color;
    }

    @Override
    public void setColor(boolean color) {
        this.color = color;
    }

    @Override
    public boolean isNull() {
        if (this.getKey() == null)
            return true;
        return false;
    }

}