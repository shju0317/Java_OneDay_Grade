package com.biz.grade.service;

import java.util.List;

import com.biz.grade.vo.StudentVO;

/*
 *  성적정보 처리
	가. 학번, 국어, 영어, 수학, 음악 점수를 키보드로 입력받습니다. 총점과 평균은 입력받지 않습니다. 
	나. 입력받은 학번과 점수는 com.biz.grade.exec.data 패키지에 score.txt 파일에 저장하여 이후 성적처리를 수행 하는데 활용합니다. 
	다, 성적정보를 저장할 때는 다음과 같은 형식으로 저장합니다. 00001:90:90:90:90:90 (순서대로 학번:국어:영어:수학:음악)
	라. 성적은 여러 번 나누어 입력할 수 있으며, 입력한 모든 성적은 score.txt 파일에 유지되어야 합니다. 
	마. 성적을 입력할 때 학번을 입력하면, 학생정보에 등록된 학생인지 판단하여 등록된 학생이면 성적을 입력할 수 있고, 
		그렇지 않으면 “등록되지 않은 학생정보”라는 메시지를 보여주고 성적입력을 진행하지 않아야 합니다. 
	바. 등록이 완료된 성적은 총점과 평균을 계산하는 method를 호출하여 총점과 평균을 계산합니다. 	
	사. 총점과 평균의 계산이 완료된 성적은 성적일람표 출력을 다음과 같이 수행합니다. 
	
	※ 선택사항 : 일람표 출력을 할 때 학번순으로 정렬을 수행하는 것도 좋습니다. 
 */

public interface ScoreService {
	public void loadScore();		// score.txt파일 읽기
	//public List<StudentVO> loadStudent(); // student.txt파일 읽기
	public void inputScore();		// 점수정보 입력
	public boolean chkScore();		// student.txt에 해당학번의 학생정보가 존재하는지 확인
	public void saveScore();		// score.txt파일 저장
	public int calcSum(int intKor, int intEng, int intMath, int intMusic);		// 총점 계산
	public int calcAvg(int sum);	// 평균 계산
	public void gradeList();		// 점수리스트 출력
}
