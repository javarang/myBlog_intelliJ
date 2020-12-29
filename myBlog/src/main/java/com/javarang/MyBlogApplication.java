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

		String enc = pbeEnc.encrypt("secureKey"); //암호화 할 내용
		//OwkU83XQDs+R5HH2ZUm7RIl30IzX3pYv
/*
			url: ENC(OwkU83XQDs+R5HH2ZUm7RIl30IzX3pYv)
			username: ENC(OwkU83XQDs+R5HH2ZUm7RIl30IzX3pYv)
			password: ENC(OwkU83XQDs+R5HH2ZUm7RIl30IzX3pYv)
*/
		System.out.println("enc = " + enc); //암호화 한 내용을 출력

		//테스트용 복호화
		String des = pbeEnc.decrypt(enc);

		System.out.println("des = " + des);
//		System.out.println("des1 = " + pbeEnc.decrypt("Rs3hbLU+Erc8cLergJza8MFwonviEO3a"));
	}
}
