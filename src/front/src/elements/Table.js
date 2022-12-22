import React from 'react';

import Copy from "./Copy";

function Table(props) {
    const DisplayData = props.dataTable.map(
        (info) => {
            return (
                <tr>
                    <td>{info.name}</td>
                    <td>{info.id}</td>
                    <td>{info.size}</td>
                    <Copy typeId={info.id} toast={props.toast}/>
                </tr>
            )
        }
    )

    return (
        <div>
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>Name</th>
                    <th>Type ID</th>
                    <th>Size</th>
                </tr>
                </thead>
                <tbody>
                {DisplayData}
                </tbody>
            </table>
        </div>
    )
}

export default Table;
