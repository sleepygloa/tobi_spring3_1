# 설명 

- CallSumText 추가
> @Before 로 미리 필요한 메서드와 파일경로를 지정.
> 테스트할 메서드 3개 sumOfNumbers, multiplyOfNumbers, concentrateOfStrings

-Calculator.java 추가
> 각 실행 메서드에서 interface 의 메서드를 구현하고, 그안에 콜백함수를 선언하여 책임을 interface로 던짐. 그의 인스턴스자체를 처리 로직으로 리턴함
> 처리로직에서는 파일을 읽는 로직과 그 이후 콜백함수를 실행시킴.

- 동일한 로직을 처리하기 위해 제너릭을 사용함.
