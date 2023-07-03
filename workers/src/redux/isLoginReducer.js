/* 

*/
const initState = false
export default function isLoginReducer (preStore = initState, action) {
	const { type } = action
	switch (type) {
		case 'logined':
			return true
		case 'exit':
			return false
		default:
			return preStore
	}
}