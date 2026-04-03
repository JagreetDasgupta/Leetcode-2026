class Solution {
    private MountainArray mountainArray;
    private int targetValue;

    public int findInMountainArray(int target, MountainArray mountainArr) {
        int arrayLength = mountainArr.length();
        this.mountainArray = mountainArr;
        this.targetValue = target;


        int left = 0;
        int right = arrayLength - 1;
        int firstTrueIndex = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (mid < arrayLength - 1 && mountainArr.get(mid) > mountainArr.get(mid + 1)) {
                firstTrueIndex = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        int peakIndex = firstTrueIndex;

        int result = searchAscending(0, peakIndex);

        if (result == -1) {
            result = searchDescending(peakIndex + 1, arrayLength - 1);
        }

        return result;
    }


    private int searchAscending(int left, int right) {
        int firstTrueIndex = -1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (mountainArray.get(mid) >= targetValue) {
                firstTrueIndex = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        if (firstTrueIndex != -1 && mountainArray.get(firstTrueIndex) == targetValue) {
            return firstTrueIndex;
        }
        return -1;
    }


    private int searchDescending(int left, int right) {
        int firstTrueIndex = -1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (mountainArray.get(mid) <= targetValue) {
                firstTrueIndex = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        if (firstTrueIndex != -1 && mountainArray.get(firstTrueIndex) == targetValue) {
            return firstTrueIndex;
        }
        return -1;
    }
}
