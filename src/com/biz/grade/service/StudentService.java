package com.biz.grade.service;

import java.util.List;

import com.biz.grade.vo.StudentVO;

//학생정보 처리
//가. 학번, 이름, 학과, 학년, 전화번호를 키보드를 통해 입력받습니다. 학번은 5자리로 설정하되 정수 5자리 이내로 입력받고
//00001 형식으로 문자열을 만들어 처리합니다. 나. 입력받은 내용은 com.biz.grade.exec.data 패키지에 student.txt 파일로 저장합니다. 
//student.txt 파일은 이후 성적처리를 수행할 때 사용할 파일입니다
//다. 학생정보를 입력할 때 여러 번에 나누어 입력하여도 이후 계속해서 추가 유지 되도록 하여야 합니
//다. 라. 파일에 저장할때는 다음과 같은 형식으로 저장합니다
//00001:홍길동:컴퓨터공학:3:010-111-1111 (순서대로 학번:이름:학과:학년:전화번호)

public interface StudentService {
	public void loadStudent();		// student.txt 파일열기
	public void inputStudent();		// 사용자로부터 학생정보 입력받기
	public boolean chkStudent();	// student.txt 파일을 열어 학생정보있는지 확인
	public void saveStudent();		// 사용자가 입력한 학생정보를 student.txt에 저장
	public List<StudentVO> getList();
}
