class Solution {
 public:
  Node* connect(Node* root) {
    Node* node = root;  
    while (node != nullptr) {
      Node dummy(0); 
      for (Node* needle = &dummy; node; node = node->next) {
        if (node->left != nullptr) { 
          needle->next = node->left;
          needle = needle->next;
        }
        if (node->right != nullptr) {  
          needle->next = node->right;
          needle = needle->next;
        }
      }
      node = dummy.next; 
    }

    return root;
  }
};