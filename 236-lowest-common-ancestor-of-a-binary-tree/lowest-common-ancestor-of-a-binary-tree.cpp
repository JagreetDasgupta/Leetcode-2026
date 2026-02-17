class Solution {
 public:
  TreeNode* lowestCommonAncestor(TreeNode* root, TreeNode* p, TreeNode* q) {
    queue<TreeNode*> q_{{root}};
    unordered_map<TreeNode*, TreeNode*> parent{{root, nullptr}};
    unordered_set<TreeNode*> ancestors; 

    while (!parent.contains(p) || !parent.contains(q)) {
      root = q_.front(), q_.pop();
      if (root->left) {
        parent[root->left] = root;
        q_.push(root->left);
      }
      if (root->right) {
        parent[root->right] = root;
        q_.push(root->right);
      }
    }

    while (p != nullptr) {
      ancestors.insert(p);
      p = parent[p];  
    }

    while (!ancestors.contains(q))
      q = parent[q];

    return q;
  }
};