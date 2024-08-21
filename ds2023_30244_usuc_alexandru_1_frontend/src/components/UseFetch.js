import { useState, useEffect } from "react";

const useFetch = (url, headers = {}) => {
  const [data, setData] = useState(null);
  const [isPending, setIsPending] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await fetch(url, {
          headers: {
            ...headers,
            // Add your authentication token to the headers
            Authorization: `Bearer ${localStorage.getItem("token")}`,
          },
        });

        if (!response.ok) {
          throw Error('Could not fetch the data for that resource');
        }

        const data = await response.json();
        setData(data);
        setIsPending(false);
        setError(null);
      } catch (error) {
        setIsPending(false);
        setError(error.message);
      }
    };

    fetchData();
  }, []);

  return { data, isPending, error };
};

export default useFetch;


// import React from "react";
// import { useState, useEffect } from "react";

// const useFetch = (url) => {
//   const [data, setData] = useState(null);
//   const [isPending, setIsPending] = useState(true);
//   const [error, setError] = useState(null);

//   useEffect(() => {
//     setTimeout(() => {
//       fetch(url)
//       .then(res => {
//         if (!res.ok) { // error coming back from server
//           throw Error('could not fetch the data for that resource');
//         } 
//         return res.json();
//       })
//       .then(data => {
//         setIsPending(false);
//         setData(data);
//         setError(null);
//       })
//       .catch(err => {
//         // auto catches network / connection error
//         setIsPending(false);
//         setError(err.message);
//       })
//     }, 300);
//   }, [url]);

//   return { data, isPending, error };
// }

// export default useFetch;

