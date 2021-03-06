package string;

/*자바의 모든~객체는 절대 피할 수 없는 최상위 객체를 두고있다.
Object 객체.개발자가 상속을 명시하지 않더라도 디폴트로 이미 상속되어 있다.
*/
class Duck{
	String name="오리";
	
	/*아래의 메서드는 Object클래스로부터 상속받은 메서드이며
	이 메서드는 객체를 스트링으로 반환하고자 할 때 동작한다.
	즉 아래의 메서드는 객체를 출력하고자 할 때 등에 자동으로 동작한다.

	아래의 메서드는 Object의 메서드이지만, 테스트로 동작하는 시점을 알기 위해 잠시
	오버라이드 해본것
	*/
	public String toString(){
		System.out.println("toString()메서드 동작");
		return "";
	}
	public static void main(String[] args){
		Duck d=new Duck();
		//System.out.println(d);//오리 자체가 아니라, 오리의 주소값
		System.out.println(new Duck());
	}
}
