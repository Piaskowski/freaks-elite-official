switch (window.location.pathname) {
    case '/admin-panel/dodaj-post':
        $('#new-post').classList.add('active');
        break;
    case '/admin-panel/dodaj-wydarzenie':
        $('#new-concert').classList.add('active');
        break;
    case '/admin-panel/zespol':
        $('#edit-bandMembers').classList.add('some');
        break;
    case '/admin-panel/band-member':
        $('#edit-bandMembers').classList.add('some');
        break;
    default:
        // code block
}