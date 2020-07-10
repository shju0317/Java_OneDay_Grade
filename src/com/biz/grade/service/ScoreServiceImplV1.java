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
import com.biz.grade.vo.ScoreVO;
import com.biz.grade.vo.StudentVO;

public class ScoreServiceImplV1 implements ScoreService {
	StudentService studentService = new StudentServiceImplV1();
	
	protected List<ScoreVO> scoreList;
	protected List<StudentVO> studentList;
	
	String scoreFile = "";
	String studentFile = "";
	String reader = "";
	String strNum = "";

	public ScoreServiceImplV1() {
		scoreList = new ArrayList<ScoreVO>();
		studentList = studentService.getList();
		scoreFile = "src/com/biz/grade/exec/data/score.txt";
		studentFile = "src/com/biz/grade/exec/data/student.txt";

		this.loadScore();
	}

	@Override
	public void loadScore() {
		scoreList.clear();

		FileReader fileReader = null;
		BufferedReader buffer = null;

		try {
			fileReader = new FileReader(scoreFile);
			buffer = new BufferedReader(fileReader);

			reader = "";
			while (true) {
				reader = buffer.readLine();
				if (reader == null) {
					break;
				}

				String[] balances = reader.split(":");

				ScoreVO scoreVO = new ScoreVO();

				scoreVO.setStrNum(balances[SplitPos.SCORE.SC_NUM]);
				scoreVO.setIntKor(Integer.valueOf(balances[SplitPos.SCORE.SC_KOR]));
				scoreVO.setIntEng(Integer.valueOf(balances[SplitPos.SCORE.SC_ENG]));
				scoreVO.setIntMath(Integer.valueOf(balances[SplitPos.SCORE.SC_MATH]));
				scoreVO.setIntMusic(Integer.valueOf(balances[SplitPos.SCORE.SC_MUSIC]));
				scoreVO.setIntSum(Integer.valueOf(balances[SplitPos.SCORE.SC_SUM]));
				scoreVO.setIntAvg(Integer.valueOf(balances[SplitPos.SCORE.SC_AVG]));

				scoreList.add(scoreVO);
			}

			buffer.close();
			fileReader.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("\n>> score.txt 파일을 찾을 수 없습니다.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void inputScore() {
		Scanner scan = new Scanner(System.in);
		
		ScoreVO sVO = new ScoreVO();

		System.out.print("학번입력(정수값  5자리 이내 ex)00001)>> ");
		strNum = scan.nextLine();
		sVO.setStrNum(strNum);

		// 5자리 이상인지 확인
		if (strNum.length() > 5) {
			System.out.println(">> 5자리 이내의 숫자를 입력하세요 :)");
			return;
		}
		
		strNum = String.format("%05d", Integer.valueOf(strNum));
		
		// 입력한 학번의 학생정보가 있는지 확인
		if (!chkScore()) {
			System.out.println("\n>> 등록되지 않은 학생정보입니다 :)");
			return;
		}
		sVO.setStrNum(strNum);

		System.out.print("국어>> ");
		String strKor = scan.nextLine();

		int intKor = 0;

		try {
			intKor = Integer.valueOf(strKor);
		} catch (Exception e) {
			System.out.print("국어점수는 숫자만 가능!");
		}
		sVO.setIntKor(intKor);

		System.out.print("영어>> ");
		String strEng = scan.nextLine();

		int intEng = 0;

		try {
			intEng = Integer.valueOf(strEng);
		} catch (Exception e) {
			System.out.print("영어점수는 숫자만 가능!");
		}
		sVO.setIntEng(intEng);

		System.out.print("수학>> ");
		String strMath = scan.nextLine();

		int intMath = 0;

		try {
			intMath = Integer.valueOf(strMath);
		} catch (Exception e) {
			System.out.print("수학점수는 숫자만 가능!");
		}
		sVO.setIntMath(intMath);

		System.out.print("음악>> ");
		String strMusic = scan.nextLine();

		int intMusic = 0;

		try {
			intMusic = Integer.valueOf(strMusic);
		} catch (Exception e) {
			System.out.print("음악점수는 숫자만 가능!");
		}
		sVO.setIntMusic(intMusic);

		int intSum = calcSum(intKor, intEng, intMath, intMusic);
		sVO.setIntSum(intSum);

		int intAvg = calcAvg(intSum);
		sVO.setIntAvg(intAvg);

		scoreList.add(sVO);
		saveScore();
	}

	@Override
	public void saveScore() {
		PrintStream outPut = null;

		try {
			outPut = new PrintStream(scoreFile);

			for (ScoreVO sVO : scoreList) {
				outPut.printf("%s:%d:%d:%d:%d:%d:%d\n", sVO.getStrNum(), sVO.getIntKor(), sVO.getIntEng(),
						sVO.getIntMath(), sVO.getIntMusic(), sVO.getIntSum(), sVO.getIntAvg());
			}
			outPut.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("\n>> score.txt 파일을 찾을 수 없습니다 :(");
		}
	}

	@Override
	public int calcSum(int intKor, int intEng, int intMath, int intMusic) {
		int sum = 0;
		sum = intKor + intEng + intMath + intMusic;
		return sum;
	}

	@Override
	public int calcAvg(int sum) {
		int avg = 0;
		avg = sum / 4;

		return avg;
	}

	@Override
	public void gradeList() {	
		System.out.println();
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("성적 일람표");
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("학번\t이름\t국어\t영어\t수학\t음악\t총점\t평균");
		System.out.println("-------------------------------------------------------------");

		int scoreSize = scoreList.size();
		int studentSize = studentList.size();

		for (int i = 0; i < scoreSize; i++) {
			ScoreVO scoreVO = scoreList.get(i);

			// score의 학번으로 studentList에서 찾기
			for (int j = 0; j < studentSize; j++) {
				StudentVO studentVO = studentList.get(j);

				// 학번이 같은 학생의 정보를 찾았으면 출력
				if (scoreVO.getStrNum().equals(studentVO.getStrNum())) {
					System.out.print(scoreVO.getStrNum() + "\t");
					System.out.print(studentVO.getStrName() + "\t");
					System.out.print(scoreVO.getIntKor() + "\t");
					System.out.print(scoreVO.getIntEng() + "\t");
					System.out.print(scoreVO.getIntMath() + "\t");
					System.out.print(scoreVO.getIntMusic() + "\t");
					System.out.print(scoreVO.getIntSum() + "\t");
					System.out.println(scoreVO.getIntAvg());
					break;
				}
			}			
		}
		System.out.println("-------------------------------------------------------------");
		
		int korSum = 0;
		int engSum = 0;
		int mathSum = 0;
		int musicSum = 0;
		
		for(ScoreVO sVO : scoreList) {
			korSum += sVO.getIntKor();
			engSum += sVO.getIntEng();
			mathSum += sVO.getIntMath();
			musicSum += sVO.getIntMusic();
		}
		System.out.printf("과목총점:\t%d\t%d\t%d\t%d\n", korSum, engSum, mathSum, musicSum);
		
		int korAvg = korSum / scoreList.size();
		int engAvg = engSum / scoreList.size();
		int mathAvg = mathSum / scoreList.size();
		int musicAvg = musicSum / scoreList.size();
		
		System.out.printf("과목평균:\t%d\t%d\t%d\t%d\n", korAvg, engAvg, mathAvg, musicAvg);
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

	}

	@Override
	public boolean chkScore() {		
		for (StudentVO sVO : studentList) {
			if (sVO.getStrNum().equals(strNum)) {
//				System.out.println(">>>>> sVO.getStrNum(): " + sVO.getStrNum());
//				System.out.println(">>>>> strNum: " + strNum);
				return true;
			}
		}
		return false;
	}
}
