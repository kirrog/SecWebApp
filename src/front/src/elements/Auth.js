import axios from "axios";
import {HOST} from "../utils/properties";
import React, {useEffect, useState} from 'react';

function Auth() {

    const [data, setData] = useState('');

    useEffect(() => {
        (async () => {
            const result = await axios(`${HOST}/auth`);
            setData(result.data);
        })();
    }, []);

    return (
        <div>
            <a href={data}>Tap here to authorize</a>
        </div>
    )
}

export default Auth;
