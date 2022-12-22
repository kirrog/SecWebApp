import React from 'react';
import {HOST} from "../utils/properties";
import axios from "axios";
import {Button} from 'primereact/button';
import {showMessage} from "../utils/notification";

function Copy(props) {
    const handleClick = async () => {
        if (localStorage.getItem("email") === "null") {
            showMessage(props.toast, "error", "Access denied", "You need to authorize first")
        } else {
            const result = await axios({
                method: 'post',
                url: `${HOST}/copy`,
                data: {
                    typeId: props.typeId,
                    email: localStorage.getItem("email")
                }
            });
            showMessage(props.toast, "success", "Response code " + result.status, result.data)
        }
    }

    return (
        <div>
            <Button label="copy" className="p-button-info p-button-text" onClick={handleClick}/>
        </div>
    )
}

export default Copy;
