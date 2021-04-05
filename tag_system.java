package java_report;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class report2_������ {

	public static void main(String[] args) {
		HashMap<String, Integer> table = new HashMap<String, Integer>();
		FileOpen(table);
		while (true) {
			Menu(); // ����ȭ�� �޴�
			switch (Input()) {
			case 1:
				Warehousing(table); // �԰�
				break;
			case 2:
				Release(table); // ���
				break;
			case 3:
				ShowInfo(table); // ��Ȳ
				break;
			case 4:
				Output(table); // ���� �� ����
				return;
			default:
				System.out.println("�ٽ� �Է��ϼ���.");
			}
		}
	}

	public static void FileOpen(HashMap<String, Integer> table) {
		String line = ""; // �޸����� ������ ���� ���ڿ��� ����
		String copy = ""; // main�۾��� ����� ���ڿ� ����
		BufferedReader br = null;
		FileReader fr = null;
		try {
			fr = new FileReader("file/info.txt");
			br = new BufferedReader(fr);
			while ((line = br.readLine()) != null) { // �޸������κ��� ���پ��޴µ� ���� ��� false ��ȯ
				copy += line; // ������ �޸��� ������ copy�� ����
			}
			if (copy != null) {
				StringTokenizer stok = new StringTokenizer(copy);
				while (stok.hasMoreTokens()) { // ���⸦ �������� ����� HashMap table�� �����Ѵ�(�޸��� �������� => product amount �����
												// �����ؼ�
												// �����ؼ� ����)
					String a = stok.nextToken(); // product �̸��� ����
					String b = stok.nextToken(); // product ������ ����
					Integer c = Integer.parseInt(b); // String���� ����� ������ int�� ����
					table.put(a, c); // HashMap�� ����
				}
			}
			System.out.println("<���� �Է� �Ϸ�>");
		} catch (IOException e1) {
			System.out.println("<���� ����, ����� �ڵ� �����˴ϴ�.>");
		}
	}

	public static void Menu() {
		System.out.println("����ȭ��");
		System.out.println("1.�԰� 2.��� 3.��Ȳ 4.����");
	}

	public static int Input() {
		Scanner in = new Scanner(System.in);
		int select;
		try {
			select = in.nextInt();
		} catch (Exception e) {
			System.out.println("�ٽ� �Է��ϼ���.");
			System.out.println("1.�԰� 2.��� 3.��Ȳ 4.����");
			return Input();
		}
		return select;
	}

	public static void Warehousing(HashMap<String, Integer> table) {
		Scanner in = new Scanner(System.in);
		String product;
		int amount;
		System.out.print("�԰��� ��ǰ : ");
		product = in.nextLine();
		System.out.print("���� : ");
		try {
			amount = in.nextInt();
			Integer cnt = table.get(product);
			if (cnt == null)
				table.put(product, amount);
			else {
				cnt += amount;
				table.put(product, cnt);
			}
			System.out.println("���� : " + table.get(product));
			System.out.println(product + "��(��) " + amount + "�� �԰� �Ǿ��� �� ������ " + table.get(product) + "�Դϴ�.");
		} catch (Exception e) {
			System.out.println("�԰� ������ �߸��Է��ϼ̽��ϴ�.");
		}
	}

	public static void Release(HashMap<String, Integer> table) {
		Scanner in = new Scanner(System.in);
		String releaseProduct;
		int releaseAmount;
		System.out.print("����� ��ǰ : ");
		releaseProduct = in.nextLine();
		Integer cnt2 = table.get(releaseProduct);
		if (cnt2 == null) {
			System.out.println("�ش� ��ǰ�� �����ϴ�. �ٽ� �Է��ϼ���.");
			return;
		}
		try {
			System.out.print("���� : ");
			releaseAmount = in.nextInt();
			if (releaseAmount > table.get(releaseProduct))
				System.out.println(releaseProduct + "������ " + releaseAmount + "���� �۽��ϴ�.");
			else {
				cnt2 -= releaseAmount;
				table.put(releaseProduct, cnt2);
				System.out.println(releaseProduct + "(��)�� " + releaseAmount + "�� ���Ǿ��� ���� �Ѽ����� "
						+ table.get(releaseProduct) + "���Դϴ�.");
				if(table.get(releaseProduct) == 0)
					table.remove(releaseProduct);
			}
		} catch (Exception e) {
			System.out.println("��� ������ �߸��Է��ϼ̽��ϴ�.");
		}
	}

	public static void ShowInfo(HashMap<String, Integer> table) {
		System.out.println("====================");
		System.out.println("��ǰ \t\t����");
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
			System.out.println("������ ����� �� �����ϴ�.");
		} finally {
			try {
				w.close();
			} catch (Exception e) {
			}
		}
	}
}