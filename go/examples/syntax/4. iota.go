package main

func main() {

	const (
		k = 3  					// itoa == 0
		
		m float32 = iota + .5	 // m float32 = 1 + .5
		n 						// n float32 = 2 + .5 (autocomplete)

		p = 9					// iota == 3
		q = iota * 2			// q = 4 * 2
		_						// _ = 5 * 2 (autocomplete)
		r						// r = 6 * 2 (autocomplete)

		s, t = iota, iota		// s, t = 7, 7
		u, v					// u, v = 8, 8 (autocomplete)
		_, w					// _, w = 9, 9 (autocomplete)
	)

	const x = iota 	// x = 0

	const (
		y = iota	// y = 0
		z			// z = 1
	)

	println(m)
	println(n)
	println(q, r)
	println(s, t, u, v, w)
	println(x, y, z)
}