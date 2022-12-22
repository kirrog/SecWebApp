import React, {useState} from 'react';
import {Button} from 'primereact/button';
import {InputText} from 'primereact/inputtext';
import axios from "axios";
import {HOST} from "../utils/properties";
import {showMessage} from "../utils/notification";

function Load(props) {
    const [url, setUrl] = useState('');

    const handleClick = async () => {
        if (localStorage.getItem("email") === "null") {
            showMessage(props.toast, "error", "Access denied", "You need to authorize first")
        } else {
            await axios({
                method: 'post',
                url: `${HOST}/load`,
                data: {
                    url: url,
                    email: localStorage.getItem("email")
                }
            }).then(props.reloadTable)
        }
    }

    return (
        <div>
            <InputText id="in" value={url} onChange={(e) => setUrl(e.target.value)}/>
            <Button label="Load" aria-label="Submit" onClick={handleClick}/>
        </div>
    )
}

export default Load;
