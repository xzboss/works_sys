function one () {
	return Math.floor(Math.random() * 10)
}
export default function shortId (length) {
	length = length ? length : 6
	let result = ''
	for (let i = 0; i < length; i++) {
		result += one()
	}
	return result
}