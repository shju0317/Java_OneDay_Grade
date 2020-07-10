package com.biz.grade.exec;

import java.util.Scanner;

import com.biz.grade.service.ScoreService;
import com.biz.grade.service.ScoreServiceImplV1;
import com.biz.grade.service.StudentService;
import com.biz.grade.service.StudentServiceImplV1;

public class ChkEx_01 {
	public static void main(String[] args) {
		
		StudentService studentService = new StudentServiceImplV1();
		ScoreService scoreService = new ScoreServiceImplV1();
		Scanner scan = new Scanner(System.in);

		while (true) {
			System.out.println();
			System.out.println("============================================================");
			System.out.println("\t\t◆ 학생 성적 관리 시스템 V1 ◆\n");
			System.out.println("\t\t         Developer: Shin Hyun-ju(shju0317)");
			System.out.println("------------------------------------------------------------");
			System.out.println("1. 학생 정보입력");
			System.out.println("2. 성적 입력");
			System.out.println("3. 학생 성적리스트 출력");
			System.out.println("-1. 종료");
			System.out.println("============================================================");
			System.out.print("업무선택>> ");

			String strMenu = scan.nextLine();
			int intMenu = 0;
			try {
				intMenu = Integer.valueOf(strMenu);
			} catch (Exception e) {
				System.out.println("\n[WARNING] 메뉴선택은 숫자로만 입력하세요 :)");
				continue;
			}

			if(intMenu == -1) {
				System.out.println("\n[TERMINATE] 업무를 종료합니다 :)");
				break;
			}else if(intMenu < 1 || intMenu >3){
				System.out.println("\n[WARNING] 선택된 업무가 없습니다.");
				continue;
			}
			
			if (intMenu == 1) {
				// 학생 정보입력
				studentService.inputStudent();
			} else if (intMenu == 2) {
				// 성적 입력
				scoreService.inputScore();
			} else if (intMenu == 3) {
				// 학생 성적리스트 출력
				scoreService.gradeList();
			} 
		}
	}
}
