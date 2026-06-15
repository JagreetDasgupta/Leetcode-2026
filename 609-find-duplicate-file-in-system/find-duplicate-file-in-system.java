class Solution {
  public List<List<String>> findDuplicate(String[] paths) {
    List<List<String>> ans = new ArrayList<>();
    Map<String, List<String>> contentToFilePaths = new HashMap<>();

    for (final String path : paths) {
      final String[] words = path.split(" ");
      final String rootPath = words[0]; 
      for (int i = 1; i < words.length; ++i) {
        final String fileAndContent = words[i];
        final int l = fileAndContent.indexOf('(');
        final int r = fileAndContent.indexOf(')');
        final String file = fileAndContent.substring(0, l);
        final String content = fileAndContent.substring(l + 1, r);
        final String filePath = rootPath + '/' + file;
        contentToFilePaths.putIfAbsent(content, new ArrayList<>());
        contentToFilePaths.get(content).add(filePath);
      }
    }

    for (List<String> filePaths : contentToFilePaths.values())
      if (filePaths.size() > 1)
        ans.add(filePaths);

    return ans;
  }
}