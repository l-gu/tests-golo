module foo

import java.util

function bar = |wat| -> println(wat)

function fct1 = {
	println("Hello from fct1")
}

function fct2 = |p1| {
	println("Hello from fct2 : parameter = " + p1 )
}

function addition = |a, b| {
	println("addition : " + a + " + " + b)
	return a + b
}

function getDate = {
	# without import : GoloCompilationException
	let d = Date()
	return d
}

function getLong = {
	# without import : OK (java.lang)
	let r = Long("222")
	return r
}

function increment = |v| {
	println("increment : " + v )
	return v + 1
}

function abs = |v| { # v expected type is BigDecimal
	println("abs : " + v )
	return v:abs()
}
