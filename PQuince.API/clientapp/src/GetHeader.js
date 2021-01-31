function getAuthHeader() {
	return `Bearer ${localStorage.getItem("keyToken")}`;
}

export default getAuthHeader;
