import axios from "axios";
import {HOST} from "./properties";

const CODE = "&code="

export async function authorize() {
    let link = window.location.href
    let tokenIndex = link.indexOf(CODE)
    if (tokenIndex !== -1) {
        let token = link.substring(
            link.indexOf(CODE) + CODE.length,
            link.lastIndexOf("&")
        );
        const result = await axios({
            method: 'post',
            url: `${HOST}/auth-token`,
            data: {
                token: token
            }
        });
        if (localStorage.getItem("email") === "null" && result.data.status === 0) {
            console.log("Successful Authorization")
            localStorage.clear()
            localStorage.setItem("email", result.data.email)
        }
    }
}
