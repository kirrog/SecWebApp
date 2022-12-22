export function showMessage(toast, severity, summary, detail) {
    toast.current.replace({severity: severity, summary: summary, detail: detail, life: 3000});
}