package com.javarang;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyBlogApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(MyBlogApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		StandardPBEStringEncryptor pbeEnc = new StandardPBEStringEncryptor();
		pbeEnc.setAlgorithm("PBEWithMD5AndDES");
		pbeEnc.setPassword("javarang"); //2번 설정의 암호화 키를 입력

		String enc = pbeEnc.encrypt("scretKey"); //암호화 할 내용
		//qqoRjSEcKeF4/JEQOyzD7Mx6Y3nvD118
		/*
			url: ENC(qqoRjSEcKeF4/JEQOyzD7Mx6Y3nvD118)
			username: ENC(qqoRjSEcKeF4/JEQOyzD7Mx6Y3nvD118)
			password: ENC(qqoRjSEcKeF4/JEQOyzD7Mx6Y3nvD118)
		*/
		System.out.println("enc = " + enc); //암호화 한 내용을 출력

		//테스트용 복호화
		String des = pbeEnc.decrypt(enc);
		System.out.println("des = " + des);
	}
}
