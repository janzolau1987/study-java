package com.yaoyaohao.study.mybatis;

public class StudentService extends BaseService {
	public Student selectStudentById(int studentId) {
		StudentMapper mapper = openSession().getMapper(StudentMapper.class);
		return mapper.selectStudentById(studentId);
	}
}
