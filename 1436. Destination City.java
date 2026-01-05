class Solution {
    public String destCity(List<List<String>> paths) {
        Set<String> departureCities = new HashSet<>();
      
        for (List<String> path : paths) {
            departureCities.add(path.get(0));
        }
      
        for (int i = 0; ; i++) {
            String destinationCity = paths.get(i).get(1);
          
            if (!departureCities.contains(destinationCity)) {
                return destinationCity;
            }
        }
    }
}
