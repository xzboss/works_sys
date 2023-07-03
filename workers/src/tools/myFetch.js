const token = localStorage.getItem('token')
export const post = async (url, body) => {
	const res = await fetch(url, {
		method: 'POST',
		body: JSON.stringify(body),
		headers: {
			'Content-Type': 'application/json',
			'Authorization': token ? `Bearer ${token}` : null,
		}
	})
	return await res.text()
}
export const get = async (url) => {
	const res = await fetch(url, {
		method: 'GET',
		headers: {
			'Authorization': token ? `Bearer ${token}` : null,
		}
	})
	const data = await res.json()
	return data
}
export const put = async (url, body) => {
	const res = await fetch(url, {
		method: 'PUT',
		body: JSON.stringify(body),
		headers: {
			'Content-Type': 'application/json',
			'Authorization': token ? `Bearer ${token}` : null,
		}
	})
	return await res.text()
}
export const del = async (url, body) => {
	const res = await fetch(url, {
		method: 'DELETE',
		body: JSON.stringify(body),
		headers: {
			'Authorization': token ? `Bearer ${token}` : null,
		}
	})
	return await res.text()
}

