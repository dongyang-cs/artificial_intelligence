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

    @Test
    public void findIndexTest() {
        int[] nums1 = new int[]{1, 2, 5, 8, 11};
        int index = findNotGreaterNum(nums1, 3, new int[2]);
        System.out.println(index);
    }

    @Test
    public void testArray() {
        int[] s = new int[]{1, 2, 3, 4};
        int[] s1 = s;
        s1[1] = 111;
        System.out.println(s[1]);
    }

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {

        final int sumLength = nums1.length + nums2.length;
        int halfSumLength = (sumLength + 1) / 2;
        final boolean isEven = sumLength % 2 == 0;
        final int nums1Last = nums1[nums1.length - 1], nums2Last = nums2[nums2.length - 1];
        final int nums1First = nums1[0], nums2First = nums2[0];

        if (nums1Last < nums2First) {
            return getMiddle(nums1, nums2);
        }

        if (nums1First > nums2Last) {
            return getMiddle(nums2, nums1);
        }


        int notGreater1 = nums1.length / 2;
        int notGreater2 = nums2.length / 2;
        int num = nums1[notGreater1];
        int notGreaterSum;
        int[] range1 = new int[]{0, nums1.length}, range2 = new int[]{0, nums2.length};
        while (true) {
            notGreater2 = findNotGreaterNum(nums2, num, range2);
            notGreaterSum = notGreater1 + notGreater2;
            if (notGreaterSum < halfSumLength) {
                range1[0] = notGreater1;
                range2[0] = notGreater2;
                notGreater1 = (range1[1] + notGreater1) / 2;
            }
            if (notGreaterSum > halfSumLength) {
                range1[1] = notGreater1;
                range2[1] = notGreater2;
                notGreater1 = (range1[0] + notGreater1) / 2;
            }

            if (notGreaterSum == halfSumLength) {
                num = nums1[notGreater1 - 1];
                if (isEven) {
                    if (notGreater2 == 0) {
                        return (double) (num + nums1[notGreater1]) / 2;
                    } else {
                        int s1 = nums1[notGreater1];
                        int s2 = nums2[notGreater2];
                        int value = s1 > s2 ? s2 : s1;
                        return (double) (num + value) / 2;
                    }
                } else {
                    return (double) num;
                }
            }

            if (range1[1] - range1[0] < 3) {
                int[] range = range1;
                range1 = range2;
                range2 = range;

                int[] nums = nums1;
                nums1 = nums2;
                nums2 = nums;

                int notGreater = notGreater1;
                notGreater1 = notGreater2;
                notGreater2 = notGreater;
            }
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


    private int findNotGreaterNum(int[] nums, final int n, int[] range) {

        if (n < nums[0]) {
            return 0;
        }
        if (n >= nums[nums.length - 1]) {
            return nums.length;
        }
        int index = nums.length / 2;
        int small, big;

        if (range[0] == 0 && range[1] == 0) {
            range[0] = 0;
            range[1] = nums.length;
        }
        if (range[1] - range[0] < 2) {
            return nums[range[0]];
        }
        while (true) {
            small = nums[index];
            big = nums[index + 1];
            if (small <= n && big > n) {
                return index + 1;
            } else if (big == n) {
                return index + 2;
            } else if (small > n) {
                range[1] = index;
                index = (index + range[0]) / 2;
            } else {
                range[0] = index;
                index = (index + range[1]) / 2;
            }
        }
    }
}
