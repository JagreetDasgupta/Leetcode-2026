class Solution {
  public int[] secondGreaterElement(int[] nums) {
    int[] ans = new int[nums.length];
    Arrays.fill(ans, -1);
    Deque<Integer> prevStack = new ArrayDeque<>();
    Deque<Integer> currStack = new ArrayDeque<>();

    for (int i = 0; i < nums.length; ++i) {
      while (!prevStack.isEmpty() && nums[prevStack.peek()] < nums[i])
        ans[prevStack.poll()] = nums[i];

      Deque<Integer> decreasingIndices = new ArrayDeque<>();
      while (!currStack.isEmpty() && nums[currStack.peek()] < nums[i])
        decreasingIndices.push(currStack.poll());
      while (!decreasingIndices.isEmpty())
        prevStack.push(decreasingIndices.poll());
      currStack.push(i);
    }

    return ans;
  }
}
