package main

type book struct {
	title string
}

// nil 값 체크로 런타임 패닉 방지
func (b *book) setTitleNilCheck(title string) {
	if b != nil {
		b.title = title
	}
}

// nil 값 체크로 런타임 패닉 방지
func (b *book) getTitleNilCheck() string {
	if b != nil {
		return b.title
	}

	return ""
}

// nil 포인터 역참조로 인한 런타임 패닉 발생 가능
func (b *book) setTitle(title string) {
	b.title = title
}

// nil 포인터 역참조로 인한 런타임 패닉 발생 가능
func (b *book) getTitle() string {
	return b.title
}


func main() {

	var b *book

	// nil 포인터 역참조로 인한 런타임 패닉 발생
	// b.setTitle("golang")
	// b.getTitle()

	// nil 값 체크로 런타임 패닉 방지
	b.setTitleNilCheck("golang")
	b.getTitleNilCheck()
}
