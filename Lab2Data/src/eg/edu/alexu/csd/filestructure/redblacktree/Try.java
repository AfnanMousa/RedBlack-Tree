package eg.edu.alexu.csd.filestructure.redblacktree;

public class Try {
    public static void main(String[] args) {
        IRedBlackTree x=new RedBlackTree();
//        INode root=x.getRoot();
//        root.setKey(2);
//        root.setValue(10);
//
//        root.setRightChild(new Node());
//        root.getRightChild().setParent(root);
//        root.getRightChild().setValue(5);
//        root.getRightChild().setKey(4);
//        root.setLeftChild(new Node());
//        root.getLeftChild().setParent(root);
//        root.getLeftChild().setValue(4);
//        root.getLeftChild().setKey(1);
//        root.getRightChild().setRightChild(new Node());
//        root.getRightChild().getRightChild().setParent(root.getRightChild());
//        root.getRightChild().getRightChild().setKey(8);
//        root.getRightChild().getRightChild().setValue(6);
//        root.getRightChild().getRightChild().setColor(true);
//       // root.setRightChild(new ImplementationNode());
        x.insert(4,"soso4");
        x.insert(0,"soso0");
        x.insert(8,"soso8");
        x.insert(1,"soso1");
        x.insert(7,"soso7");
        x.insert(9,"soso9");
        RBTreePrinter uu=new RBTreePrinter();
        uu.print(x.getRoot());
        x.delete(0);
        uu.print(x.getRoot());
        x.delete(1);
        uu.print(x.getRoot());
        x.delete(7);
        uu.print(x.getRoot());
        x.delete(4);
        uu.print(x.getRoot());
        x.delete(8);
        uu.print(x.getRoot());
        x.delete(9);
        uu.print(x.getRoot());

        //        x.insert(17,"soso1");
//        x.insert(21,"soso1");
//        x.insert(24,"soso1");
//        x.insert(26,"soso1");
//        x.insert(29,"soso1");



       // System.out.println(x.delete(17));
        //System.out.println(x.delete(2));
//        System.out.println(x.delete(133));
//        System.out.println(x.delete(169));
//        System.out.println(x.delete(117));
//        System.out.println(x.delete(173));
//        System.out.println(x.delete(17));
//        System.out.println(x.delete(181));
    }
}
