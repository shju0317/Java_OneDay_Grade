package com.biz.grade.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.biz.grade.config.SplitPos;
import com.biz.grade.vo.StudentVO;

public class StudentServiceImplV1 implements StudentService {

	protected List<StudentVO> studentList;
	String strNum = "";
	String studentFile = "";

	public StudentServiceImplV1() {
		studentList = new ArrayList<StudentVO>();
		studentFile = "src/com/biz/grade/exec/data/student.txt";
		this.loadStudent();
	}

	
	@Override
	public void loadStudent() {
		studentList.clear();

		FileReader fileReader = null;
		BufferedReader buffer = null;

		try {
			fileReader = new FileReader(studentFile);
			buffer = new BufferedReader(fileReader);

			String reader = "";
			while (true) {
				reader = buffer.readLine();
				if (reader == null) {
					break;
				}
				// System.out.println(reader);

				String[] students = reader.split(":");

				StudentVO studentVO = new StudentVO();

				studentVO.setStrNum(students[SplitPos.STUDENT.ST_NUM]);
				studentVO.setStrName(students[SplitPos.STUDENT.ST_NAME]);
				studentVO.setStrDept(students[SplitPos.STUDENT.ST_DEPT]);
				studentVO.setIntGrade(Integer.valueOf(students[SplitPos.STUDENT.ST_GRADE]));
				studentVO.setStrPhone(students[SplitPos.STUDENT.ST_PHONE]);

				studentList.add(studentVO);
			}
			buffer.close();
			fileReader.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println(">> student.txt 파일을 찾을 수 없습니다 :(");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void inputStudent() {
		// 사용자로부터 학생정보 입력받기
		StudentVO stdVO = new StudentVO();

		Scanner scan = new Scanner(System.in);

		System.out.print("학번입력(정수값  5자리 이내 ex)00001)>> "); // 학번
		strNum = scan.nextLine();

		// 5자리 이상인지 확인
		if (strNum.length() > 5) {
			System.out.println(">> 5자리 이내의 숫자를 입력하세요 :)");
			return;
		}

		strNum = String.format("%05d", Integer.valueOf(strNum));
		
		// student.txt 파일에 입력한 학번의 학생이 있는지 확인
		if (!chkStudent()) {
			System.out.println("\n>> 해당 학생정보가 이미 존재합니다 :)");
			return;
		}
		stdVO.setStrNum(strNum);

		System.out.print("이름입력>> "); // 이름
		String strName = scan.nextLine();
		stdVO.setStrName(strName);

		System.out.print("학과입력>> "); // 학과
		String strDept = scan.nextLine();
		stdVO.setStrDept(strDept);

		System.out.print("학년입력>> "); // 학년
		String strGrade = scan.nextLine();

		int intGrade = 0;
		try {
			intGrade = Integer.valueOf(strGrade);
		} catch (Exception e) {
			System.out.println("[WARNING] 학년은 숫자만 입력가능!");
			System.out.println(stdVO.getStrName() + "학생정보를 다시 입력해주세요 :D");
			return;
		}

		if (intGrade < 1 || intGrade > 4) {
			System.out.println("1~4학년까지만 입력가능!");
			System.out.println(stdVO.getStrName() + "학생정보를 다시 입력해주세요 :D");
			return;
		}

		stdVO.setIntGrade(intGrade);

		System.out.print("전화번호 입력( - 포함 ex)010-1111-2222)>> "); // 전화번호
		String strPhone = scan.nextLine();
		stdVO.setStrPhone(strPhone);

		studentList.add(stdVO);
		
		saveStudent();
	}

	@Override
	public boolean chkStudent() { // 학생정보있는지 확인
		for (StudentVO sVO : studentList) {
			if (sVO.getStrNum().equals(strNum)) {
				//System.out.println(">>>>> sVO.getStrNum(): " + sVO.getStrNum());
				//System.out.println(">>>>> strNum: " + strNum);
				return false;
			}
		}
		return true;
	}

	@Override
	public void saveStudent() { // 사용자가 입력한 학생정보를 student.txt에 저장
		PrintStream outPut = null;

		try {
			outPut = new PrintStream(studentFile);

			for (StudentVO sVO : studentList) {
				outPut.printf("%s:%s:%s:%d:%s\n", sVO.getStrNum(), sVO.getStrName(), sVO.getStrDept(),
						sVO.getIntGrade(), sVO.getStrPhone());
			}
			outPut.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("\n>> student.txt 파일을 찾을 수 없습니다 :(");
		}
	}

	@Override
	public List<StudentVO> getList() {
		return studentList;
	}
}
