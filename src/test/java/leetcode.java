import org.junit.Test;

/**
 * Created by Arvin Alfod on 2018/4/25.
 */
public class leetcode {

    @Test
    public void test() {
        int[] nums1 = new int[]{1, 2, 5, 8, 11};
        int[] nums2 = new int[]{7, 9, 10};
        double x = findMedianSortedArrays(nums1, nums2);
        System.out.println(x);
    }

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {

        final int sumLength = nums1.length + nums2.length;
        final int halfSumLength = sumLength / 2;
        final boolean isEven = sumLength % 2 == 0;
        final int nums1Last = nums1[nums1.length - 1], nums2Last = nums2[nums2.length - 1];
        final int nums1First = nums1[0], nums2First = nums2[0];

        if (nums1Last < nums2First) {
            return getMiddle(nums1, nums2);
        }

        if (nums1First > nums2Last) {
            return getMiddle(nums2, nums1);
        }


        int index1 = nums1.length / 2;
        int index2 = nums2.length / 2;
        int n = nums1[index1];
        int index;
        int[] index1r = new int[]{0, nums1.length - 1}, index2r = new int[]{0, nums2.length - 1};
        boolean isNum1 = true;
        while (true) {
            if (isNum1) {
                index2 = findIndex(nums2, n);
                if (index1 + index2 < halfSumLength) {
                    index1r[0] = index1;
                    index2 = (index2r[1] + index2) / 2;
                    n = nums2[index2];
                } else if (index1 + index2 > halfSumLength) {
                    index1r[1] = index1;
                    index2 = (index2r[0] + index2) / 2;
                    n = nums2[index2];
                } else {
                    if (isEven) {
                        return (double) (n + nums1[index1 - 1]) / 2;
                    } else {
                        return (double) n;
                    }
                }

            } else {
                index1 = findIndex(nums1, n);
                if (index1 + index2 < halfSumLength) {
                    index2r[0] = index2;
                    index1 = (index1r[1] + index1) / 2;
                    n = nums1[index1];
                } else if (index1 + index2 > halfSumLength) {
                    index2r[1] = index2;
                    index1 = (index1r[0] + index1) / 2;
                    n = nums1[index1];
                } else {
                    if (isEven) {
                        return (double) (nums1[index1] + nums2[index2 - 1]) / 2;
                    } else {
                        return (double) n;
                    }
                }
            }
            isNum1 = !isNum1;
        }


    }

    private double getMiddle(int[] first, int[] last) {
        final int sumLength = first.length + last.length;
        final int halfSumLength = sumLength / 2;
        final boolean isEven = sumLength % 2 == 0;
        final int nums1Last = first[first.length - 1], nums2Last = last[last.length - 1];
        final int nums1First = first[0], nums2First = last[0];

        if (isEven) {
            if (halfSumLength < first.length) {
                return (first[halfSumLength] + first[halfSumLength - 1]) / 2;
            } else if (halfSumLength > first.length + 1) {
                int startIndex = halfSumLength - first.length;
                return (first[startIndex] + first[startIndex - 1]) / 2;
            } else {
                return (nums1Last + nums2First) / 2;
            }
        } else {
            if (halfSumLength < first.length) {
                return first[halfSumLength];
            } else {
                return last[halfSumLength - first.length];
            }
        }
    }


    public int findIndex(int[] nums, final int n) {
        int i = nums.length / 2;
        int temN;
        int[] range = new int[]{0, nums.length};
        if (n < nums[0]) {
            return 0;
        }
        if (n >= nums[nums.length - 1]) {
            return nums.length;
        }
        while (true) {
            temN = nums[i];
            if (n == temN || (n > temN && n < nums[i + 1])) {
                return i + 1;
            } else if (n > temN) {
                range[0] = i;
                i = (i + range[1]) / 2;
            } else {
                range[1] = i;
                i = (i + range[0]) / 2;
            }
        }
    }
}
