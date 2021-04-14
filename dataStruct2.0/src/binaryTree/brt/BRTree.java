package binaryTree.brt;

import java.util.ArrayList;
import java.util.List;

/**
 * 红黑树
 *
 * @author anxi
 * @version 2021/4/7 19:06
 */
public class BRTree {

    private static final boolean RED = false;
    private static final boolean BLACK = true;

    private Node root;

    public BRTree() {

    }

    /**
     * https://www.cnblogs.com/CarpenterLee/p/5503882.html
     */
    public void put(int key, int value) {
        if (root == null) {
            root = new Node(key, value, null, null, null, BLACK);
            return;
        }
        Node cur = root;
        Node parent = null;
        int cmp = -1;
        while (cur != null) {
            parent = cur;
            cmp = Integer.compare(key, cur.key);
            if (cmp < 0) {
                cur = cur.left;
            } else if (cmp > 0) {
                cur = cur.right;
            } else {
                cur.value = value;
                return;
            }
        }

        //插入节点为红色，不改变黑色路径长度
        Node newNode = new Node(key, value, null, null, parent, RED);
        if (cmp < 0) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }

        fixAfterInsert(newNode);
    }

    private void fixAfterInsert(Node node) {
        //只有父节点是红色才调整
        while (node != null && node.parent != null && node.parent.color == RED) {
            if (parentOf(node) == leftOf(grandParentOf(node))) {
                //父节点是左子节点
                if (colorOf(rightOf(grandParentOf(node))) == RED) {
                    //叔叔节点为红，叔、父、祖变色
                    setColor(parentOf(node), BLACK);
                    setColor(rightOf(grandParentOf(node)), BLACK);
                    setColor(grandParentOf(node), RED);
                    node = grandParentOf(node);
                } else {
                    //叔叔节点为黑
                    //子节点内插，左旋至外插
                    if (node == rightOf(parentOf(node))) {
                        node = parentOf(node);
                        rotateLeft(node);
                    }
                    //外插父节点变黑、祖节点变红，右旋
                    setColor(parentOf(node), BLACK);
                    setColor(grandParentOf(node), RED);
                    rotateRight(grandParentOf(node));
                }
            } else {
                //父节点是右子节点
                if (colorOf(leftOf(grandParentOf(node))) == RED) {
                    setColor(parentOf(node), BLACK);
                    setColor(leftOf(grandParentOf(node)), BLACK);
                    setColor(grandParentOf(node), RED);
                    node = grandParentOf(node);
                } else {
                    if (node == leftOf(parentOf(node))) {
                        node = parentOf(node);
                        rotateRight(node);
                    }
                    setColor(parentOf(node), BLACK);
                    setColor(grandParentOf(node), RED);
                    rotateLeft(grandParentOf(node));
                }
            }
        }
        root.color = BLACK;
    }

    public int get(int key) {
        Node cur = getNode(key);
        return cur == null ? Integer.MIN_VALUE : cur.value;
    }

    private Node getNode(int key) {
        Node cur = root;
        while (cur != null) {
            int cmp = Integer.compare(key, cur.key);
            if (cmp < 0) {
                cur = cur.left;
            } else if (cmp > 0) {
                cur = cur.right;
            } else {
                return cur;
            }
        }
        return null;
    }

    /**
     * https://www.cnblogs.com/CarpenterLee/p/5525688.html
     */
    public void remove(int key) {
        Node toRemove = getNode(key);
        if (toRemove == null) {
            return;
        }
        if (toRemove.left != null && toRemove.right != null) {
            //删除节点有左右子节点，寻找后继节点替换
            Node successor = findSuccessor(toRemove);
            toRemove.key = successor.key;
            toRemove.value = successor.value;
            toRemove = successor;
        }

        Node replacement = toRemove.left != null ? toRemove.left : toRemove.right;
        if (replacement != null) {
            //删除节点有子节点
            replacement.parent = toRemove.parent;
            if (toRemove.parent == null) {
                root = replacement;
            } else if (toRemove == toRemove.parent.left) {
                toRemove.parent.left = replacement;
            } else {
                toRemove.parent.right = replacement;
            }
            //删除节点
            toRemove.left = toRemove.right = toRemove.parent = null;
            if (toRemove.color == BLACK) {
//                fixAfterDelete(replacement);
            }
        } else {
            //删除节点无子节点
            if (toRemove.parent == null) {
                //删除的是根节点
                root = null;
            } else {
                if (toRemove.color == BLACK) {
                    //删除节点自身看做是替代节点，翻转后删除
//                    fixAfterDelete(toRemove);
                }

                if (toRemove.parent.left == toRemove) {
                    toRemove.parent.left = null;
                } else {
                    toRemove.parent.right = null;
                }
                toRemove.parent = null;
            }
        }
    }

    private Node findSuccessor(Node node) {
        Node cur = node.right;
        while (cur.left != null) {
            cur = cur.left;
        }
        return cur;
    }

    private void fixAfterDelete(Node node) {
        while (node != root && colorOf(node) == BLACK) {
            //左子
            if (node == leftOf(parentOf(node))) {
                Node brother = rightOf(parentOf(node));
                //兄弟节点颜色为红，先变色左旋
                if (colorOf(brother) == RED) {
                    setColor(parentOf(node), RED);
                    setColor(brother, BLACK);
                    rotateLeft(parentOf(node));
                    //更新brother：bro为之前bro的左子节点，必然为黑
                    brother = rightOf(parentOf(node));
                }
                if (colorOf(leftOf(brother)) == BLACK && colorOf(rightOf(brother)) == BLACK) {
                    //brother可变红
                    setColor(brother, RED);
                    node = parentOf(node);
                } else {
                    //brother的右子为黑，变色右旋为红
                    if (colorOf(rightOf(brother)) == BLACK) {
                        setColor(brother, RED);
                        setColor(leftOf(brother), BLACK);
                        rightOf(brother);
                        brother = rightOf(parentOf(node));
                    }
                    //brother替换为父节点颜色，左旋
                    setColor(brother, colorOf(parentOf(node)));
                    setColor(parentOf(node), BLACK);
                    setColor(rightOf(brother), BLACK);
                    rotateLeft(brother);
                    node = root;
                }
                //右子
            } else {

            }
        }
        setColor(node, BLACK);
    }

    private void rotateLeft(Node node) {
        if (node != null) {
            Node rc = node.right;
            //转移右子节点的左子节点
            node.right = rc.left;
            if (rc.left != null) {
                rc.left.parent = node;
            }
            //转移右子节点
            Node parent = node.parent;
            if (parent == null) {
                root = rc;
            } else if (leftOf(parent) == node) {
                parent.left = rc;
            } else {
                parent.right = rc;
            }
            rc.parent = parent;

            rc.left = node;
            node.parent = rc;
        }
    }

    private void rotateRight(Node node) {
        if (node != null) {
            Node lc = node.left;
            node.left = lc.right;
            if (lc.right != null) {
                lc.right.parent = node;
            }
            Node parent = node.parent;
            if (parent == null) {
                root = lc;
            } else if (leftOf(parent) == node) {
                parent.left = lc;
            } else {
                parent.right = lc;
            }
            lc.parent = parent;

            lc.right = node;
            node.parent = lc;
        }
    }

    private boolean colorOf(Node node) {
        return node == null ? BLACK : node.color;
    }

    private void setColor(Node node, boolean color) {
        if (node != null) {
            node.color = color;
        }
    }

    private Node parentOf(Node node) {
        return node == null ? node : node.parent;
    }

    private Node grandParentOf(Node node) {
        return parentOf(parentOf(node));
    }

    private Node leftOf(Node node) {
        return node == null ? node : node.left;
    }

    private Node rightOf(Node node) {
        return node == null ? node : node.right;
    }

    public List<Integer> visit() {
        java.util.List<Integer> keys = new ArrayList<>();
        visit(root, keys);
        return keys;
    }

    private void visit(Node node, List<Integer> result) {
        if (node == null) {
            return;
        }
        visit(node.left, result);
        result.add(node.key);
        visit(node.right, result);
    }

    private class Node {

        private int key;
        private int value;
        private Node left;
        private Node right;
        private Node parent;
        private boolean color;

        public Node(int key, int value, Node left, Node right, Node parent, boolean color) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
            this.parent = parent;
            this.color = color;
        }
    }

    public static void main(String[] args) {
        BRTree tree = new BRTree();
        List<Integer> values = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            values.add((int) (Math.random() * 1000));
        }
        for (Integer value : values) {
            tree.put(value, 1);
        }
        List<Integer> keys = tree.visit();
        validate(keys);
        for (int i = 0; i < 100; i += 10) {
            tree.remove(keys.get(i));
        }
        keys = tree.visit();
        validate(keys);
    }

    private static void validate(List<Integer> keys) {
        System.out.print(keys);
        System.out.println();
        for (int i = 1; i < keys.size(); i++) {
            if (keys.get(i) <= keys.get(i - 1)) {
                throw new RuntimeException();
            }
        }
    }
}
