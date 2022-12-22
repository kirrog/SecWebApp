import axios from "axios";
import {HOST} from "../utils/properties";
import React, {useEffect, useState} from 'react';

function Auth() {

    const [data, setData] = useState('');

    useEffect(() => {
        (async () => {
            const result = await axios(`${HOST}/auth`);
            console.log("Reg link: " + result.data["link"]);
            setData(result.data["link"]);
        })();
    }, []);

    return (
        <div>
            <a href={data}>Tap here to authorize</a>
        </div>
    )
}

export default Auth;
