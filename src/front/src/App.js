import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import Table from "./elements/Table";
import {useRef} from "react";
import "primeicons/primeicons.css";
import "primereact/resources/themes/lara-light-indigo/theme.css";
import "primereact/resources/primereact.min.css";
import {Toast} from "primereact/toast";
import Load from "./elements/Load";
import Auth from "./elements/Auth";
import React, {useEffect, useState} from 'react';
import {getFiles} from "./utils/files";
import {AUTH_TIME} from "./utils/properties";
import {authorize} from "./utils/authorization";

function App() {
    const toast = useRef(null);
    const [dataTable, setDataTable] = useState([]);

    localStorage.setItem("email", null)
    setInterval(authorize, AUTH_TIME);

    useEffect(() => {
        getFiles().then(response => response.json()).then(files => setDataTable(files))
    }, [])

    const updateDataTable = () => {
        getFiles().then(response => response.json()).then(files => setDataTable(files))
    }

    return (
        <div className="App">
            <div>
                <div>
                    <div>
                        <Toast ref={toast} position="top-right"/>
                    </div>
                    <div className="main-container">
                        <div className="container">
                            <Load toast={toast} reloadTable={updateDataTable}/>
                        </div>
                        <div className="container">
                            <Auth/>
                        </div>
                    </div>
                    <div className="table-container">
                        <Table toast={toast} dataTable={dataTable}/>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default App;
