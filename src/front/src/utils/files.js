import {HOST} from "./properties";

export function getFiles() {
    return fetch(`${HOST}/files`,
        {
            method: "GET"
        }
    )
}