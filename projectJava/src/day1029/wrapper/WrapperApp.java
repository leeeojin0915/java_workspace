package day1029.wrapper;

/*
 * var x="5"-->정수 5로 바꾸려면? js : parseInt(x);
 * 
 * 자바에서는 모든 기본 자료형마다 1:1 대응하는 래퍼클래스를 지원
 * 정수 = byte : Byte, short : Short, int : Integer, long: Long
 * 실수 = float : Float, double : Double
 * 
 * Wrapper클래스는 기본자료형을 객체화 시킴으로서 훨씬 다양한 데이터 처리를 지원해준다
 * ex) 숫자형문자를 실제 숫자로 변환
 *		기본자료형을 객체자료형으로 형변환
 *		기타 여러 메서드를 지원함으로서, 기본자료형을 보다 다양하게 제어할 수 있다. 
 * */

public class WrapperApp {

	public static void main(String[] args) {
		String x="6";
		int y=4;
		System.out.println(x+y);//모두 string이 되버림 따라서 이때 +기호는 연결자가 된다
		
		int z=Integer.parseInt(x);//static 메서드이므로 Integer.찍고 접근
		System.out.println(z+y);
		
		Integer i=5; //자바의 클래스 원칙으로 본다면 원래 불가능 하지만 Integer클래스는 기본자료형과
						//관련된 객체이므로 마치 기본자료형처럼 데이터를 대입할 수 잇다.
						//사실상 내부적으로 5라는 기본자료형이 객체화 된 것이다.(Boxing:기본데이터를 박스로 감쌌다)
						//Wrapper클래스는 box로 감싸다(wrapper)에거 근거한 말
		
		int k=i; //원칙상 객체자료형을 기본자료형에 대입하는 것은 불가능하지만 위와 마찬가지로 래퍼클래스가
					//기본자료형과 관련된 객체이므로, 내부적으로 unBoxing에 의해 기본자료형으로 변환이 된 것.
		//결론 : 기본자료형을 --> 객체화(Boxing), 객체자료형을-->기본자료형으로(unBoxing)
		//box로 감싸고 다시 꺼내는 객체를 가리켜 Wrapper클래스라 부른다.
		
		
		
		
		
		
		
	}

}