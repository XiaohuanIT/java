import java.util.Arrays;

public class HeapSort {
	public static void main(String[] args){
		int[] arr = {6,7,1,2,8,10,0};
		System.out.println(Arrays.toString(arr));
		sort(arr);
		System.out.println(Arrays.toString(arr));
	}

	public static void sort(int[] arr){
		if(arr==null || arr.length<2){
			return;
		}

		for(int i=0; i<arr.length; i++){
			headInsert(arr, i);
		}

		int heapSize = arr.length;
		while (heapSize > 0){
			swap(arr, 0, --heapSize);
			heapify(arr, 0, heapSize);
		}
	}

	public static void headInsert(int[] arr, int index){
		while(arr[index] > arr[(index-1)/2]){
			swap(arr, index, (index-1)/2);
			index  = (index-1)/2;
		}
	}

	public static void heapify(int[] arr, int index, int size){
		// 获取 index 的左孩子节点
		int left = 2 * index +1;
		// 形成堆条件是：遍历时候不能超过size边界
		while(left < size){
			// 首先保证右孩子存在，然后比较左、右孩子大小
			int largest = left + 1 < size && arr[left+1] > arr[left] ? left+1 : left ;
			// 让当前节点，与左右孩子节点中大的那个相比较
			largest = arr[largest] > arr[index] ? largest : index;

			// 如果最大的就是当前节点，那么就不用做啥
			if(largest == index){
				break;
			}

			// 如果当前节点不是比左、右节点大，那么就立即进行交换
			swap(arr, index, largest);

			// 把index变成左右孩子比较出来更大的那个的位置（虽然此时已经和父节点交换了位置）
			index = largest;
			left = largest * 2 +1;

			// 接下来继续去遍历，以largest为父节点，跟孩子节点互动
		}
	}

	private static void swap(int[] arr, int i, int j){
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j]  = temp;
	}
}
