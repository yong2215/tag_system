package java_report;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class report2_재고관리 {

	public static void main(String[] args) {
		HashMap<String, Integer> table = new HashMap<String, Integer>();
		FileOpen(table);
		while (true) {
			Menu(); // 시작화면 메뉴
			switch (Input()) {
			case 1:
				Warehousing(table); // 입고
				break;
			case 2:
				Release(table); // 출고
				break;
			case 3:
				ShowInfo(table); // 현황
				break;
			case 4:
				Output(table); // 저장 및 종료
				return;
			default:
				System.out.println("다시 입력하세요.");
			}
		}
	}

	public static void FileOpen(HashMap<String, Integer> table) {
		String line = ""; // 메모장의 내용을 받을 문자열을 생성
		String copy = ""; // main작업에 사용할 문자열 생성
		BufferedReader br = null;
		FileReader fr = null;
		try {
			fr = new FileReader("file/info.txt");
			br = new BufferedReader(fr);
			while ((line = br.readLine()) != null) { // 메모장으로부터 한줄씩받는데 없는 경우 false 반환
				copy += line; // 복사한 메모장 내용을 copy에 저장
			}
			if (copy != null) {
				StringTokenizer stok = new StringTokenizer(copy);
				while (stok.hasMoreTokens()) { // 띄어쓰기를 기준으로 나누어서 HashMap table에 저장한다(메모장 저장형식 => product amount 띄어쓰기로
												// 구분해서
												// 저장해서 가능)
					String a = stok.nextToken(); // product 이름을 받음
					String b = stok.nextToken(); // product 갯수를 받음
					Integer c = Integer.parseInt(b); // String으로 저장된 갯수를 int로 변경
					table.put(a, c); // HashMap에 저장
				}
			}
			System.out.println("<파일 입력 완료>");
		} catch (IOException e1) {
			System.out.println("<파일 없음, 종료시 자동 생성됩니다.>");
		}
	}

	public static void Menu() {
		System.out.println("시작화면");
		System.out.println("1.입고 2.출고 3.현황 4.종료");
	}

	public static int Input() {
		Scanner in = new Scanner(System.in);
		int select;
		try {
			select = in.nextInt();
		} catch (Exception e) {
			System.out.println("다시 입력하세요.");
			System.out.println("1.입고 2.출고 3.현황 4.종료");
			return Input();
		}
		return select;
	}

	public static void Warehousing(HashMap<String, Integer> table) {
		Scanner in = new Scanner(System.in);
		String product;
		int amount;
		System.out.print("입고할 제품 : ");
		product = in.nextLine();
		System.out.print("수량 : ");
		try {
			amount = in.nextInt();
			Integer cnt = table.get(product);
			if (cnt == null)
				table.put(product, amount);
			else {
				cnt += amount;
				table.put(product, cnt);
			}
			System.out.println("수량 : " + table.get(product));
			System.out.println(product + "가(이) " + amount + "개 입고 되었고 총 수량은 " + table.get(product) + "입니다.");
		} catch (Exception e) {
			System.out.println("입고 갯수를 잘못입력하셨습니다.");
		}
	}

	public static void Release(HashMap<String, Integer> table) {
		Scanner in = new Scanner(System.in);
		String releaseProduct;
		int releaseAmount;
		System.out.print("출고할 제품 : ");
		releaseProduct = in.nextLine();
		Integer cnt2 = table.get(releaseProduct);
		if (cnt2 == null) {
			System.out.println("해당 제품이 없습니다. 다시 입력하세요.");
			return;
		}
		try {
			System.out.print("수량 : ");
			releaseAmount = in.nextInt();
			if (releaseAmount > table.get(releaseProduct))
				System.out.println(releaseProduct + "수량이 " + releaseAmount + "보다 작습니다.");
			else {
				cnt2 -= releaseAmount;
				table.put(releaseProduct, cnt2);
				System.out.println(releaseProduct + "(이)가 " + releaseAmount + "개 출고되었고 남은 총수량은 "
						+ table.get(releaseProduct) + "개입니다.");
				if(table.get(releaseProduct) == 0)
					table.remove(releaseProduct);
			}
		} catch (Exception e) {
			System.out.println("출고 갯수를 잘못입력하셨습니다.");
		}
	}

	public static void ShowInfo(HashMap<String, Integer> table) {
		System.out.println("====================");
		System.out.println("제품 \t\t수량");
		System.out.println("====================");
		for (String str : table.keySet()) {
			System.out.println(str + "\t\t" + table.get(str));
		}
		System.out.println("====================");
	}

	public static void Output(HashMap<String, Integer> table) {
		FileWriter w = null;
		try {
			w = new FileWriter("file/info.txt");
			for (String str : table.keySet()) {
				w.write(str + " " + table.get(str) + " ");
			}
		} catch (IOException ioe) {
			System.out.println("파일을 출력할 수 없습니다.");
		} finally {
			try {
				w.close();
			} catch (Exception e) {
			}
		}
	}
}