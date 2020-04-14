package eg.edu.alexu.csd.filestructure.redblacktree;

import javax.management.RuntimeErrorException;

public class RedBlackTree implements IRedBlackTree{
    private INode root = new Node();
    private INode Found=new Node();
    private int equal = 0;
    public RedBlackTree() {
//        root.setLeftChild(new Node());
//        root.setRightChild(new Node());
//        root.setParent(new Node());
    }

    @Override
    public INode getRoot() {
        if (root==null)
            return null;
        return root;
    }

    @Override
    public boolean isEmpty() {
        if (root.getKey()==null)
            return true;
        return false;
    }

    @Override
    public void clear() {
        root = new Node();
        root.setParent(new Node());
    }

    @Override
    public Object search(Comparable key) {
        if (key==null) {
            throw new RuntimeErrorException (null);
        }
        if (root.getKey()==null) return null;
        INode help=new Node();
        help=root;
        INode Output=new Node();
        while (/*(help!=null)||*/((help.getLeftChild()!=null)||(help.getRightChild()!=null))){
            if((help.getKey().compareTo(key)>0)&&(help.getLeftChild()!=null)){
                help=help.getLeftChild();
            }
            else if((help.getKey().compareTo(key)<0)&&(help.getRightChild()!=null)){
                help=help.getRightChild();
            }
            else {
                Output=help;
                break;
            }
        }
        if (Output.isNull())  return null;
        Found=Output;
        return Output.getValue();

    }

    @Override
    public boolean contains(Comparable key) {
        if (key==null) {
            throw new RuntimeErrorException (null);
        }
        if(search(key)!=null){
            return true;
        }else {
            return false;
        }

    }
    private void rightRotation (INode node) {
        INode tempLeft = node.getLeftChild();
        node.setLeftChild(tempLeft.getRightChild());

        if (node.getLeftChild()!=null) {
            node.getLeftChild().setParent(node);
        }
        tempLeft.setParent(node.getParent());

        if (tempLeft.getParent()==null||tempLeft.getParent().isNull()) {
            root=tempLeft;
        }else if (node == node.getParent().getLeftChild()) {
            node.getParent().setLeftChild(tempLeft);
        }else {
            node.getParent().setRightChild(tempLeft);
        }

        tempLeft.setRightChild(node);
        node.setParent(tempLeft);
    }

    private void lefttRotation (INode node) {
        INode tempRight = node.getRightChild();
        node.setRightChild(tempRight.getLeftChild());

        if (node.getRightChild()!=null) {
            node.getRightChild().setParent(node);
        }
        tempRight.setParent(node.getParent());

        if (tempRight.getParent()==null||tempRight.getParent().isNull()) {
            root=tempRight;
        }else if (node == node.getParent().getLeftChild()) {
            node.getParent().setLeftChild(tempRight); ////////
        }else {
            node.getParent().setRightChild(tempRight);
        }

        tempRight.setLeftChild(node);
        node.setParent(tempRight);
    }
    private INode insertIntoTree(INode root,INode child) {
        if (root.isNull()) {
            root.setKey(child.getKey());;                   //this is more right
            root.setValue(child.getValue());
            root.setColor(child.getColor());
            root.setRightChild(new Node());                  //those are important
            root.setLeftChild(new Node());                   //for testNormalInsertWithRandomData
        }else {
            if (child.getKey().compareTo(root.getKey())>0) {
                if (root.getRightChild()==null) {
                    root.setRightChild(new Node());
//              root.setLeftChild(new Node());
                }
                INode tempRoot=root.getRightChild();
                if(tempRoot.isNull()) child.setParent(root);
                root.setRightChild(insertIntoTree( root.getRightChild(),child));
                root.getRightChild().setParent(root);
            }else if (child.getKey().compareTo(root.getKey())<0) {
                if (root.getLeftChild()==null) {
                    root.setRightChild(new Node());
//              root.setLeftChild(new Node());
                }
                INode tempRoot=root.getLeftChild();
                if(tempRoot.isNull()) child.setParent(root);
                root.setLeftChild(insertIntoTree( root.getLeftChild(),child));
                root.getLeftChild().setParent(root);
            }else {
                equal=1;
                root.setValue(child.getValue());
            }
        }
        return root;
    }
    private void check (INode node) {
        INode parent = null;
        INode grandParent = null;
        INode uncle;
        while(node.getKey()!=root.getKey() && node.getColor()!=node.BLACK /*&& node.getColor()!=node.getParent().getColor()*/){
            parent = node.getParent();
            grandParent = parent.getParent();
            if (grandParent==null) {
                node = parent;
                continue;
            }
            if (parent== grandParent.getRightChild()) {
                uncle = grandParent.getLeftChild();
            }else {
                uncle = grandParent.getRightChild();
            }

            if(parent.getColor()==node.RED) {
                if (uncle!=null&&uncle.getColor()==node.RED) {
                    uncle.setColor(node.BLACK);
                    parent.setColor(node.BLACK);
                    if (grandParent!=root)
                        grandParent.setColor(node.RED);
                    node = grandParent;
                }else if (uncle==null||uncle.getColor()==node.BLACK) {
                    if (grandParent.getLeftChild()!=null&&parent.getKey()==grandParent.getLeftChild().getKey()) {
                        parent = grandParent.getLeftChild();
                        if (parent.getRightChild()!=null&&node.getKey()==parent.getRightChild().getKey()) {
                            node = parent.getRightChild();
                            lefttRotation(parent);
                            node=parent;
                            parent = node.getParent();
                        }
                        rightRotation(grandParent);
                        parent.setColor(grandParent.getColor());
                        grandParent.setColor(node.RED);
                        node = parent;
                    }else {
                        if (parent.getLeftChild()!=null&&node.getKey()==parent.getLeftChild().getKey()) {
                            node = parent.getLeftChild();
                            rightRotation(parent);
                            node=parent;
                            parent = node.getParent();
                        }
                        lefttRotation(grandParent);
                        parent.setColor(grandParent.getColor());
                        grandParent.setColor(node.RED);
                        node = parent;
                    }
                }
            }else {
                node = parent;
            }
        }
        if (root.getColor()==node.RED) root.setColor(node.BLACK);
    }
    @Override
    public void insert(Comparable key, Object value) {
        if (key==null||value == null) {
            throw new RuntimeErrorException (null);
        }
        INode child = initialize (key, value);
        root = insertIntoTree(root,child);
        if (root.getLeftChild().isNull()&&root.getRightChild().isNull())
            root.setColor(root.BLACK);
        if (equal==0) {
            check(child);
        }equal=0;
/*      if (root.isNull()) {
            root = child;
        }
        INode temp,tempRoot,finallRoot,current;
        temp = root;
        while (temp!=null) {
            if (temp.getKey().compareTo(key)>0) {
                if ( temp.getLeftChild()==null) {
                    temp.setKey((key));
//                  root.setLeftChild(temp);
                }
                temp = temp.getLeftChild();
            }else if (temp.getKey().compareTo(key)<0) {
                if (temp.getRightChild()==null) {
                    temp.setKey(key);
    //              root.setRightChild(temp);
                }
                temp=temp.getRightChild();
            }else {
                temp = temp.getLeftChild();
            }
        }*/

/*      int f =0;
        if (this.isEmpty()) {
            root.setKey(key);;
            root.setValue(value);
        }else {
            tempRoot = new Node (root);
            current= new Node (root);
            finallRoot= new Node (root);
            if (key.compareTo(tempRoot.getKey())>0) {
                root.getRightChild().setKey(key);;
            }
            if (!tempRoot.isNull()) {
                if (key.compareTo(tempRoot.getKey())>0) {

//                  System.out.println(x.getRightChild().isNull());
//                  if (tempRoot.getRightChild().isNull()) {
                        tempRoot.setParent(tempRoot);

                        tempRoot= tempRoot.getRightChild();

//                      }
                }else if (key.compareTo(tempRoot.getKey())<0) {
//                  if (tempRoot.getLeftChild().isNull())
                    tempRoot.setParent(tempRoot);
                        tempRoot = tempRoot.getLeftChild();
                    f=1;
                }
                if (!tempRoot.isNull())
                    current = tempRoot;
//              System.out.println(temp.getKey());
            }
            current.setKey(key);
            if (f==0) {
                current.setRightChild(current);
//              finallRoot.setRightChild(current);
            }
            else {
                current.setLeftChild(current);
//              finallRoot.setLeftChild(current);
                f=0;
            }
                root=current;
            int k =0;

        }*/
    /*  temp = root;
        temp.setKey(555);
        System.out.print(temp.getKey());*/
    }
    private Node initialize (Comparable key, Object value) {
        Node child = new Node();

        child.setKey(key);
        child.setValue(value);
        child.setColor(child.RED);
/*      child.setLeftChild(new Node());
        child.setRightChild(new Node());
        child.setParent(new Node());*/
        return child;
    }
    @Override
    public boolean delete(Comparable key) {
        if(key==null){
            throw new RuntimeErrorException(null);
        }else if (search(key)==null) {
            return false;
        }
        else {
            System.out.println(key);
            search(key);

            INode Successor=new Node();//Right Side the bigger
            INode Predecessor=new Node();//left Side the smaller
            INode help=Found;
            while ((!help.getRightChild().isNull())||(!help.getLeftChild().isNull())){
                if ((!help.getRightChild().isNull())&&((help.getLeftChild().isNull())||(!help.getLeftChild().isNull()))){
                    help=SuccessorMathod(help.getRightChild());
                    Comparable tempkey=Found.getKey();
                    Object tempvalue=Found.getValue();
                    Found.setKey(help.getKey());
                    Found.setValue(help.getValue());
                    help.setKey(tempkey);
                    help.setValue(tempvalue);
                    Found=help;
                }
                else {
                    help=PredecessorMathod(help.getLeftChild());
                    Comparable tempkey=Found.getKey();
                    Object tempvalue=Found.getValue();
                    Found.setKey(help.getKey());
                    Found.setValue(help.getValue());
                    help.setKey(tempkey);
                    help.setValue(tempvalue);
                    Found=help;
                }
            }
            if(Found.getColor()){
                DeleteNode(Found);
                return true;
            }
            else {
               if (be(Found,key)){
                   search(key);
                   if(Found==root){
                       clear();
                       return true;
                   }
                   else {
                       DeleteNode(Found);
                       return true;
                   }
                }
               else {
                   return false;
               }
            }

        }
    }
    private boolean be(INode found,Comparable key){
        if (Case1(found)){
            return true;
        }
        else if (Case2(found,key)){
            return true;
        }
        else if (Case3(found,key)){

            return true;
        }
        else if (Case4(found)){

            return true;
        }
        else if (Case5(found)){

            return true;
        }
        else {
            return false;
        }
    }
    public void DeleteNode(INode found){
//        found.setValue(null);
//        found.setKey(null);
//        found.setRightChild(null);
//        found.setLeftChild(null);
//        found.setColor(false);
        if((Dimantion(found)=="Left")){
            Found.getParent().setLeftChild(new Node());
        }
        else {
            Found.getParent().setRightChild(new Node());
        }
    }
    public String Dimantion (INode found){
//        if(found.getParent()==null){
//            System.out.println(found.getKey()+" "+found.getColor()+" "+found.getRightChild().getKey());
//        }
//       if(found.getParent().getLeftChild()==null){
//            System.out.println("xgjkl");
//        }
        if(((!found.getParent().getLeftChild().isNull()))&&(found.getParent().getLeftChild().getKey().compareTo(found.getKey())==0)){
            return "Left";
        }
        else {
            return "Right";
        }
    }
    public INode  SiblingMathod(INode found){
        if(found.getParent()==null){
            return new Node();
        }
        else if(Dimantion(found)=="Left"){
            return found.getParent().getRightChild();
        }
        else {
            return found.getParent().getLeftChild();
        }

    }
    public INode  SuccessorMathod(INode Successor){
        while (!Successor.getLeftChild().isNull()){
            Successor=Successor.getLeftChild();
        }
        return Successor;
    }
    public INode  PredecessorMathod(INode Predecessor){
        while (!Predecessor.getRightChild().isNull()){
            Predecessor=Predecessor.getRightChild();
        }
        return Predecessor;
    }

    public INode LeftRotation(INode x){
        INode y = x.getRightChild();
        x.setRightChild(y.getLeftChild());
        if(!y.getLeftChild().isNull()){
            y.getLeftChild().setParent(x);
        }
        y.setParent(x.getParent());
        if(x.getParent()==null){ //x is root
            root=y;
        }
        else if(Dimantion(x)=="Left") {
            x.getParent().setLeftChild(y);
        }
        else {
            x.getParent().setRightChild(y);
        }
        y.setLeftChild(x);
        x.setParent(y);
        return y;
    }
    public INode RightRotation(INode x){
        INode y = x.getLeftChild();
        x.setLeftChild(y.getRightChild());
        if(!y.getRightChild().isNull()){
            y.getRightChild().setParent(x);
        }
        y.setParent(x.getParent());
        if(x.getParent()==null){ //x is root
            root=y;
        }
        else if(Dimantion(x)=="Left") {
            x.getParent().setLeftChild(y);
        }
        else {
            x.getParent().setRightChild(y);
        }
        y.setRightChild(x);
        x.setParent(y);
        return y;

    }
    public Boolean Case1(INode found){
        if(found==root){
            return true;
        }
        return false;
    }
    public Boolean Case2(INode found,Comparable key){ //Node x is black and its sibling w is black and both of w's children are black
        INode Uncle=SiblingMathod(found);
        // DeleteNode(Found);
//        if(Uncle.getLeftChild().isNull())
        INode found2=found;

        if ((Uncle.isNull())||((!Uncle.isNull())&&((!Uncle.getColor())&&(((!Uncle.getLeftChild().getColor())&&(!Uncle.getRightChild().getColor())))))){
            if ((Uncle.isNull())||((!Uncle.isNull())&&((!Uncle.getColor())&&(((!Uncle.getLeftChild().getColor())&&(!Uncle.getRightChild().getColor())))))){
                if(Uncle.isNull()){
                    if(found2.getParent().getColor()){
                        found2.getParent().setColor(false);
                        return true;
                    }
                    else {
                       return be(found2.getParent(),key);
                    }
                }
                else {
                    if(Uncle.getParent().getColor()){
                        Uncle.getParent().setColor(false);
                        Uncle.setColor(true);
                        return true;
                    }
                    else {//decide on case 1, 2, 3, or 4 from here. Note that we have a new w now.
                        Uncle.setColor(true);
                        return  be(Uncle.getParent(),key);
//
//                        if(Uncle.getParent()==root){
//                            return true;
//                        }
//                        else {
////                            Uncle=SiblingMathod(Uncle.getParent());
//                          return   be(Uncle.getParent(),key);
//                        }
                    }
                }

            }
//            if ((Case1(Uncle))){
//                return true;
//            }
//            if ((Case3(SiblingMathod(Uncle),key))){
//                return true;
//            }
//            else if(Case4(SiblingMathod(Uncle))){
//                return true;
//            }
//            else if(Case5(SiblingMathod(Uncle))){
//                return true;
//            }
        }
        return false;
    }

    public Boolean Case3(INode found,Comparable key){ //Node x is black and its sibling w is red
        INode Uncle=SiblingMathod(found);
        if ((Uncle.getColor())&&((!Uncle.getLeftChild().getColor())&&(!Uncle.getRightChild().getColor()))) {
            Uncle.setColor(false);
            Uncle.getParent().setColor(true);
            if(Dimantion(Uncle)=="Left"){
                Uncle=RightRotation(Uncle.getParent());
                search(key);
                return be(found,key);
//                if(!Found.getParent().getColor()){
//                    return be(Found.getParent(),key);
//                }
//               else {
//                   return true;
//                }
//                if(!Found.getParent().getColor()){
//                    if(Case4(Found.getParent())){
//                        return true;
//                    }
//                    else if(Case5(Found.getParent())){
//                        return true;
//                    }
//                    else if (Case2(Found.getParent(),key)){
//                        return true;
//                    }
//                    return true;
//                }
//                else {
//                    return true;
//                }

            }
            else if(Dimantion(Uncle)=="Right"){
                Uncle=LeftRotation(Uncle.getParent());
                search(key);
                return be(found,key);
//                if(!Found.getParent().getColor()){
//                    return be(Found.getParent(),key);
//                }
//               else {
//                   return  true;
//                }
//                if(!Found.getParent().getColor()){
//                    if(Case4(Found.getParent())){
//                        return true;
//                    }
//                    else if(Case5(Found.getParent())){
//                        return true;
//                    }
//                    else if (Case2(Found.getParent(),key)){
//                        return true;
//                    }
//                    return true;
//                }
//                else {
//                    return true;
//                }
            }
            // decide on case 2, 3, or 4 from here
        }
        return false;
    }
    public Boolean Case4(INode found){
        INode Uncle=SiblingMathod(found);
        if(Dimantion(found)=="Left"){
            if((!Uncle.isNull())&&(((!Uncle.getColor())&&(Uncle.getLeftChild().getColor()))&&((!Uncle.getRightChild().getColor())))){
                Uncle.getLeftChild().setColor(false);
                Uncle.setColor(true);
                Uncle= RightRotation(Uncle);
                if(Case5(SiblingMathod(Uncle))){ //. Proceed to case 5.
                    return true;
                }
            }
        }
        else if(Dimantion(found)=="Right") {
            if((!Uncle.isNull())&&(((!Uncle.getColor())&&(!Uncle.getLeftChild().getColor()))&&((Uncle.getRightChild().getColor())))){
                Uncle.getRightChild().setColor(false);
                Uncle.setColor(true);
                Uncle= LeftRotation(Uncle);
                if(Case5(SiblingMathod(Uncle))) {//. Proceed to case 5.
                    return true;
                }
            }
        }
        return false;
    }
    public boolean Case5(INode found){ //Node x is black and its sibling w is black and
        INode Uncle=SiblingMathod(found);
        if(Dimantion(found)=="Left"){
            if((!Uncle.isNull())&&((!Uncle.getColor())&&((Uncle.getRightChild().getColor())))){
                Uncle.setColor(Uncle.getParent().getColor());
                Uncle.getParent().setColor(false);
                Uncle.getRightChild().setColor(false);
                LeftRotation(Uncle.getParent());
                return true;
            }
        }
        else if (Dimantion(found)=="Right"){
            if((!Uncle.isNull())&&(((!Uncle.getColor())&&(Uncle.getLeftChild().getColor())))){
                Uncle.setColor(Uncle.getParent().getColor());
                Uncle.getParent().setColor(false);
                Uncle.getLeftChild().setColor(false);
                RightRotation(Uncle.getParent());
                return true;
            }
        }
        return false;
    }

}